import io.fabric8.kubernetes.api.model.{Namespace, Pod}
import io.fabric8.kubernetes.client.{BaseKubernetesClient, KubernetesClient, Config, ConfigBuilder, DefaultKubernetesClient, NamespacedKubernetesClient}
import io.fabric8.kubernetes.api.model.Secret

import scala.jdk.CollectionConverters.*

trait K8sClient {
  def close: Unit

  def allPodsNames: Seq[String]

  def allPods: Seq[Pod]

  def pods(namespace: String): Seq[Pod]

  def podsNames(namespace: String): Seq[String]

  def namespaces: Seq[Namespace]

  def namespacesNames: Seq[String]

  def podLogs(namespace: String, podName: String, tailing: Int): String

  def secret(namespace: String, secretName: String): Secret

  def allSecrets: Seq[Secret]

  def pod(namespace: String, name: String): Pod

  def contextsNames: Seq[String]

  def currentContextName: String
}

class K8sClientImpl(client: BaseKubernetesClient[NamespacedKubernetesClient]) extends K8sClient {
  override def close: Unit = client.close()

  override def allPodsNames: Seq[String] = allPods.map(_.getMetadata).map(_.getName)

  override def allPods: Seq[Pod] = client.pods.inAnyNamespace.list.getItems.asScala.toSeq

  override def pods(namespace: String): Seq[Pod] = client.pods.inNamespace(namespace).list.getItems.asScala.toSeq

  override def pod(namespace: String, name: String): Pod = client.pods.inNamespace(namespace).withName(name).get()

  override def podsNames(namespace: String): Seq[String] = pods(namespace).map(_.getMetadata).map(_.getName)

  override def namespaces: Seq[Namespace] = client.namespaces.list.getItems.asScala.toSeq

  override def namespacesNames: Seq[String] = namespaces.map(_.getMetadata).map(_.getName)

  override def podLogs(namespace: String, podName: String, tailing: Int = 10): String = client.pods.inNamespace(namespace).withName(podName).tailingLines(tailing).getLog()

  override def secret(namespace: String, secretName: String): Secret = client.secrets().inNamespace(namespace).withName(secretName).get()

  override def allSecrets: Seq[Secret] = client.secrets().inAnyNamespace().list.getItems.asScala.toSeq

  override def contextsNames: Seq[String] = new ConfigBuilder().build().getContexts.asScala.toSeq.map(_.getName)

  override def currentContextName: String = new ConfigBuilder().build().getCurrentContext.getName

}

object K8sClientFactory {
  def defaultClient = new K8sClientImpl(new DefaultKubernetesClient)
}
