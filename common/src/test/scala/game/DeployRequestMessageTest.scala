// Copyright (c) 2017 PSForever
package game

import org.specs2.mutable._
import net.psforever.packet._
import net.psforever.packet.game._
import net.psforever.types.Vector3
import scodec.bits._

class DeployRequestMessageTest extends Specification {
  val string = hex"4b 4b00 7c01 40 0cf73b52aa6a9300"

  "decode" in {
    PacketCoding.DecodePacket(string).require match {
      case DeployRequestMessage(player_guid, vehicle_guid, unk1, unk2, unk3, pos) =>
        player_guid mustEqual PlanetSideGUID(75)
        vehicle_guid mustEqual PlanetSideGUID(380)
        unk1 mustEqual 2
        unk2 mustEqual 0
        unk3 mustEqual false
        pos.x mustEqual 4060.1953f
        pos.y mustEqual 2218.8281f
        pos.z mustEqual 155.32812f
      case _ =>
        ko
    }
  }

  "encode" in {
    val msg = DeployRequestMessage(
      PlanetSideGUID(75),
      PlanetSideGUID(380),
      2, 0, false,
      Vector3(4060.1953f, 2218.8281f, 155.32812f)
    )
    val pkt = PacketCoding.EncodePacket(msg).require.toByteVector

    pkt mustEqual string
  }
}
