package com.seanmcapp.service

import com.seanmcapp.config.{InstagramConf, TelegramConf}
import com.seanmcapp.helper.{HttpRequestBuilder, JsonProtocol}
import com.seanmcapp.model._
import com.seanmcapp.repository._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import spray.json._

object InstagramService extends HttpRequestBuilder with JsonProtocol {

  private val telegramConf = TelegramConf()
  private val instagramConf = InstagramConf()
  private val instagramAccounts = Map(
    "ui.cantik" -> "[\\w. ]+[\\w]'\\d\\d".r,
    "ugmcantik" -> "[\\w ]+\\. [\\w]+ \\d\\d\\d\\d".r,
    "undip.cantik" -> "[\\w ]+\\. [\\w]+ \\d\\d\\d\\d".r
  )

  def flow: Future[Iterable[InstagramUser]] = {
    Future.sequence(instagramAccounts.map { account =>
      val accountName = account._1
      val accountRegex = account._2
      val fetchResult = getPage(accountName, None)
      val photoRepoFuture = PhotoRepo.getAll
      val customerRepoFuture = CustomerRepo.getAllSubscribedCust

      for {
        photoRepo <- photoRepoFuture
        customerRepo <- customerRepoFuture
      } yield {
        val regexFilter = accountRegex
        val unsavedPhotos = fetchResult.nodes.collect {
          case item if !(photoRepo.contains(item.id) || regexFilter.findFirstIn(item.caption).isEmpty) =>
            item.copy(caption = regexFilter.findFirstIn(item.caption).get
              .replace("\\n","%0A")
              .replace("#", "%23"))
        }

        unsavedPhotos.map { node =>
          val photo = Photo(node.id, node.thumbnailSrc, node.date, node.caption, accountName)
          PhotoRepo.update(photo)

          /*
          customerRepo.map { subscriber =>
            getTelegramSendPhoto(telegramConf.endpoint, subscriber.id, photo, "bahan ciol baru: ")
          }
          */

          // uncomment this for dev env
          // getTelegramSendPhoto(telegramConf.endpoint, 274852283L, photo, "bahan ciol baru: ")
        }
        fetchResult.copy(nodes = unsavedPhotos)
      }
    })
  }

  def getPage(account: String,
              lastId: Option[String] = None): InstagramUser = {

    val request = getInstagramPageRequest(account, lastId)
    val response = request.asString
    val instagramUser = response.body.parseJson.convertTo[InstagramUser]
    val tmpResult = instagramUser.nodes
    val lastIdRes = tmpResult.lastOption.map(_.id)
    val nextResult = if (tmpResult.nonEmpty)
      getPage(account, lastIdRes).nodes
    else
      Seq.empty

    instagramUser.copy(nodes = tmpResult ++ nextResult)
  }

}