package br.com.deveficiente.ingressos.compartilhado;

import jakarta.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.springframework.util.Assert;

import java.util.LinkedHashMap;
import java.util.Map;

public class Log5WBuilder {

  public static class ProntoParaLogar {

    private Map<String, String> parametros = new LinkedHashMap<>();

    public ProntoParaLogar(
        String metodo, String oQueEstaAcontecendo, Map<String, String> infosAdicionais) {

      parametros.put("metodo", metodo);
      parametros.put("oQueEstaAcontecendo", oQueEstaAcontecendo);

      parametros.putAll(infosAdicionais);
    }

    /**
     * Cada informação adicionada tenta responder o Why e o How e dos 5w
     *
     * @param chave chave para dar contexto para a informacao
     * @param informacao detalhe da informacao
     * @return
     */
    public ProntoParaLogar adicionaInformacao(String chave, Object informacao) {
      Assert.hasText(chave, "A chave não pode ser vazia");
      Assert.notNull(informacao,"A informacao para logar nao pode ser nula");
      Assert.isNull(parametros.get(chave), "A chave já foi adicionada");

      parametros.put(chave, informacao.toString());
      return this;
    }

    /**
     * Loga com nível de debug
     *
     * @param logger
     * @return texto que foi logado
     */
    public String debug(@NotNull Logger logger) {
      String json = JsonHelper.json(this.parametros);
      logger.debug(json);

      return json;
    }

    /**
     * Loga com nível de info
     *
     * @param logger
     * @return texto que foi logado
     */
    public String info(@NotNull Logger logger) {
      String json = JsonHelper.json(this.parametros);
      logger.info(json);

      return json;
    }

    /**
     * Loga com nível de erro
     *
     * @param logger
     * @return texto que foi logado
     */
    public String erro(@NotNull Logger logger) {
      String json = JsonHelper.json(this.parametros);
      logger.error(json);

      return json;
    }

    /**
     * Loga com nível de erro associando uma exception
     *
     * @param logger
     * @param exception
     * @return texto que foi logado
     */
    public String erro(@NotNull Logger logger, @NotNull Exception exception) {
      String json = JsonHelper.json(this.parametros);
      logger.error(json, exception);

      return json;
    }

    /**
     * Para o caso de só querer capturar a mensagem que seria logada
     *
     * @return
     */
    public String mensagem() {
      return JsonHelper.json(this.parametros);
    }
  }

  public static class OQueEstaAcontecendo {

    private String metodo;
    private String oQueEstaAcontecendo;
    private Map<String, String> infosAdicionais = new LinkedHashMap<>();

    public OQueEstaAcontecendo(String metodo, String oQueEstaAcontecendo) {
      this.metodo = metodo;
      this.oQueEstaAcontecendo = oQueEstaAcontecendo;
    }

    /**
     * Cada informação adicionada tenta responder o Why e o How e dos 5w
     *
     * @param chave chave para dar contexto para a informacao
     * @param informacao detalhe da informacao
     * @return
     */
    public ProntoParaLogar adicionaInformacao(String chave, Object informacao) {
      Assert.hasText(chave, "A chave não pode ser vazia");
      Assert.notNull(informacao,"A informacao para logar nao pode ser nula");
      Assert.isNull(infosAdicionais.get(chave), "A chave já foi adicionada");

      infosAdicionais.put(chave, informacao.toString());
      return new ProntoParaLogar(metodo, oQueEstaAcontecendo, infosAdicionais);
    }
  }

  public static class Metodo {

    private String metodo;

    public Metodo(String metodo) {
      Assert.hasText(metodo, "O método não pode ser vazio");
      this.metodo = metodo;
    }

    /**
     * O momento tenta responder o What do 5ws
     *
     * @return
     */
    public OQueEstaAcontecendo oQueEstaAcontecendo(String oQueEstaAcontecendo) {
      Assert.hasText(oQueEstaAcontecendo, "O momento não pode ser vazio");
      return new OQueEstaAcontecendo(metodo, oQueEstaAcontecendo);
    }
  }

  /**
   * O método tenta responder o Where do 5ws
   *
   * @param metodo texto indicando qual o método que está sendo executado naquel momento
   * @return
   */
  public static Metodo metodo(String metodo) {
    return new Metodo(metodo);
  }

  /**
   * O método tenta responder o Where do 5ws
   *
   * <p>Encontra o método que originou a chamada do log automaticamente. Acho que vale a pena deixar
   * claro que atualmente acessamos a Thread corrente e inspecionamos a stack trace(sei que é
   * detalhe de implementaçao, mas achei importante).
   *
   * @return
   */
  public static Metodo metodo() {
    StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
    /*
     * Explicação do numero 2: A posicao zero é a chamada do getStackTrace,
     * a posicao um é a invocação do próprio metodo(). Aí a dois fica
     * sendo o ponto de origem.
     */
    return new Metodo(stackTrace[2].getMethodName());
  }
}
