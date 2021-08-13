object Main {

  def main(args: Array[String]): Unit = {

    val client = K8sClientFactory.defaultClient
    println(client.currentContextName)
    client.close
  }
}