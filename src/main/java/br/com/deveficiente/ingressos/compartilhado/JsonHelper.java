package br.com.deveficiente.ingressos.compartilhado;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;

import java.util.Map;

/**
 * Classe que serve como função para gerar e desserializar um json.
 *
 * @author albertoluizsouza
 */
public class JsonHelper {

  private static ObjectMapper mapper = new ObjectMapper();

  static {
    mapper.registerModule(new Jdk8Module());
  }

  public static String json(Object object) {
    try {
      return mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }

  @SuppressWarnings("unchecked")
  public static Map<String, String> desserializa(String json) {
    try {
      /*
       * Alberto: #naoSeiExplicar Quando a mensagem estava desserializada simplesmente passando
       * um map, eu acho que ele estava tentando inferir de maneira inteligente o tipo do dado.
       * Quando era um int, ele tentava colocar Integer e aí, se em algum lugar fosse ser usado como
       * String dava pau de cast. Isso aconteceu lá na classe {ProcessaCorrecaoViaModelosMachineLearning}
       */
      return mapper.readValue(json, new TypeReference<Map<String, String>>() {});
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
