package com.seanmcapp.helper

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import com.seanmcapp.model._

import spray.json._

trait JsonProtocol extends DefaultJsonProtocol with SprayJsonSupport {

  implicit val photoFormat = jsonFormat5(Photo)

  implicit val instagramNodeFormat = InstagramProtocol.InstagramNodeFormat

  implicit val instagamUserFormat = InstagramProtocol.InstagramUserFormat

  implicit val telegramUpdateFormat = TelegramProtocol.TelegramUpdateFormat

  implicit val telegramMessageFormat = TelegramProtocol.TelegramMessageFormat

  implicit val telegramCallbackQueryFormat = TelegramProtocol.TelegramCallbackQueryFormat

  implicit val telegramUserFormat = TelegramProtocol.TelegramUserFormat

  implicit val telegramChatFormat = TelegramProtocol.TelegramChatFormat

  implicit val telegramMessageEntityFormat = TelegramProtocol.TelegramMessageEntityFormat

  implicit val broadcastMessageFormat = BroadcastMessageProtocol.BroadcastMessageFormat

}