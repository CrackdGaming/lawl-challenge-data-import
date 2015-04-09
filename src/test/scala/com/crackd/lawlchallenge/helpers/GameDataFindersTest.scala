package com.crackd.lawlchallenge.helpers

import com.crackd.lawlchallenge.gametypes._
import com.crackd.lawlchallenge.helpers.GameDataFinders._
import com.crackd.test.JsonLoader
import org.scalatest.{FlatSpec, Matchers}
import play.api.libs.json._

/**
 * Created by tahrens on 4/9/15.
 */
class GameDataFindersTest extends FlatSpec with Matchers {
  it should "lookup champion id by participant id" in {
    val json = JsonLoader.load("/championIdForParticipantId.json")
    json.championIdForParticipantId(1) shouldBe 81
    json.championIdForParticipantId(2) shouldBe 82
    json.championIdForParticipantId(3) shouldBe 83
  }

  "events" should "return a list of all events" in {
    val json = JsonLoader.load("/events.json")
    json.events shouldBe List(makeEvent("EVENT_A"), makeEvent("EVENT_B"), makeEvent("EVENT_C", Some(Point(100,100))), makeEvent("EVENT_B"))
  }

  "eventsWithPositions" should "return a list of all events with position information" in {
    val json = JsonLoader.load("/events.json")
    json.events.withPositions shouldBe List(makeEvent("EVENT_C", Some(Point(100,100))))
  }

  "eventsWithEventType" should "only return a list of events matching the given event type" in {
    val json = JsonLoader.load("/events.json")
    json.events.withType("EVENT_C") shouldBe List(makeEvent("EVENT_C", Some(Point(100,100))))
  }

  def makeEvent(e: EventType, p: Option[Point] = None) = Json.obj(
    "eventType" -> e,
    "position" -> p.map(pp => Json.obj(
      "x" -> pp.x,
      "y" -> pp.y
    )).getOrElse(JsNull).asInstanceOf[JsValue]
  )
}
