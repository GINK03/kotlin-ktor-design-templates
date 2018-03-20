# Ktor Design Template

## Requirementes
- gradleをupgrade
- **Oracle Java 1.8**をインストール

## Getting Started.
Ktorと呼ばれるKotlinのWebフレームワークでHTTP(S)サーバを作成して、動作させます  

マイクロサービスや簡単なWebPageの作成に適しています。  

Ktorは若くマイナーなフレームワークなので、なかなかコンパイルと実行までの道のりが遠く、知見としてまとめます

## GradleのUpgrade
Ubuntuにデフォルトで入るGradleは、古く、依存が解決できないので、Upgradeします。
```console
$ sudo add-apt-repository ppa:cwchien/gradle
$ sudo apt-get update
$ sudo apt upgrade gradle
```

## Oracle Java 1.8をインストール
OpenJDKだとJNIのライブラリに不足があり、コンパイルできないなので、Oracle版を利用します
```console
$ sudo add-apt-repository ppa:webupd8team/java
$ sudo apt-get update
$ sudo apt-get install oracle-java8-installer
```

## コンパイル
```console
$ gradle build
```

## 実行
```console
$ gradle run
```

## 依存をまとめたjarの作成
```console
$ gradle jar
```

## 単純にコンテンツを返す
```kotlin
  install(Routing) {
    get("/hello") {
      call.respondText("Hello, World!")
    }
  }
```

## URLパラメータを読む
```kotlin
    get("/v1/item/{key}") {
      val item = model.items.firstOrNull { it.key == call.parameters["key"] }
      if (item == null)
        call.respond(HttpStatusCode.NotFound)
      else
        call.respond(item)
    }
```

## HTMLをkotlinx.htmlで作成する
```kotlin
    get("/html") {
      call.respondHtml {
        head {
            title { +"HTML Application" }
        }
        body {
            h1 { +"Sample application with HTML builders" }
            widget {
                +"Widgets are just functions"
            }
        }
      }
    }
```

## postメソッドでjsonを受け取る
```kotlin
    post("/post") {
      val receive = call.receive<Map<String, String>>()
      println(receive)
      call.respondWrite {
        appendln("this is post")
      }
    }
```
クライアント側のcurlリクエスト
```console
$ curl -d '{"key1":"value1", "key2":"value2"}' -H "Content-Type: application/json" -X POST http://localhost:8080/post
```
