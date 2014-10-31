package com.github.feodorov.backend

import spray.http.{ContentTypes, HttpCharsets, HttpEntity, MediaTypes}
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller
import spray.json._

/**
 * @author kfeodorov 
 * @since 31.10.14
 */

//spray-json 1.3.0 does introduce some breaking changes, which cause it to be incompatible with the current spray releases.
//https://groups.google.com/forum/#!msg/spray-user/oCKjHmUMDb0/1AxfCTxu7w8J
trait SprayJsonSupport {

  implicit def sprayJsonUnmarshallerConverter[T](reader: RootJsonReader[T]) =
    sprayJsonUnmarshaller(reader)
  implicit def sprayJsonUnmarshaller[T: RootJsonReader] =
    Unmarshaller[T](MediaTypes.`application/json`) {
      case x: HttpEntity.NonEmpty ⇒
        val json = JsonParser(x.asString(defaultCharset = HttpCharsets.`UTF-8`))
        jsonReader[T].read(json)
    }
  implicit def sprayJsonMarshallerConverter[T](writer: RootJsonWriter[T])(implicit printer: JsonPrinter = PrettyPrinter) =
    sprayJsonMarshaller[T](writer, printer)
  implicit def sprayJsonMarshaller[T](implicit writer: RootJsonWriter[T], printer: JsonPrinter = PrettyPrinter) =
    Marshaller.delegate[T, String](ContentTypes.`application/json`) { value ⇒
      val json = writer.write(value)
      printer(json)
    }
}

object SprayJsonSupport extends SprayJsonSupport

