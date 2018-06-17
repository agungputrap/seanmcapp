package com.seanmcapp.repository

import scala.concurrent.Future

case class Photo(id: String, thumbnailSrc: String, date: Long, caption: String, account: String)

trait PhotoRepo {

  def getAll: Future[Set[String]]

  def getLatest: Future[Option[Photo]]

  def getRandom: Future[Option[Photo]]

  def update(photo: Photo): Future[Option[Photo]]

}
