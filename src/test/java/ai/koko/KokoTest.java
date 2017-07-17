package ai.koko;

import java.util.Map;
import java.util.Map.Entry;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Properties;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.google.gson.Gson;

class Dotenv {
  public void load() throws IOException {
    File env = new File(".env");
    Properties variables = new Properties();
    variables.load(new FileInputStream(env));
    for (Entry<Object, Object> variable : variables.entrySet()) {
      System.setProperty(variable.getKey().toString(), variable.getValue().toString());
    }
  }
}

public class KokoTest extends TestCase {
  public KokoTest( String testName ) throws IOException {
    super( testName );

    new Dotenv().load();

    Koko.auth = System.getProperty("KOKO_AUTH");
  }

  public static Test suite() {
    return new TestSuite( KokoTest.class );
  }

  public void testTrackContent() {
    Map<String, String> content = new HashMap<String, String>();
    content.put("text", "123");
    content.put("format", "plain");

    List<String> classifiers = new ArrayList();
    classifiers.add("crisis");

    Map<String, Object> options = new HashMap<String, Object>();
    options.put("classifiers", classifiers);

    Map<String, Object> contentOptions = new HashMap<String, Object>();
    contentOptions.put("id", "123");
    contentOptions.put("created_at", Instant.now().toString());
    contentOptions.put("user_id", "123");
    contentOptions.put("type", "post");
    contentOptions.put("content", content);
    contentOptions.put("group_id", "123");
    contentOptions.put("options", options);

    Koko.trackContent(contentOptions);

    contentOptions.remove("type");

    try {
      Koko.trackContent(contentOptions);
    }

    catch (Exception e) {
      System.out.println(e);
      assert e.getMessage().equals("Required property type was not present.");
    }
  }

  public void testTrackFlag() {
    Map<String, String> target = new HashMap<String, String>();
    target.put("content_id", "123");

    List<Map> targets = new ArrayList();
    targets.add(target);

    List<String> reasons = new ArrayList();
    reasons.add("crisis");

    Map<String, Object> flagOptions = new HashMap<String, Object>();
    flagOptions.put("id", "123");
    flagOptions.put("created_at", Instant.now().toString());
    flagOptions.put("flagger_id", "123");
    flagOptions.put("reasons", reasons);
    flagOptions.put("targets", targets);

    Koko.trackFlag(flagOptions);

    flagOptions.remove("flagger_id");

    try {
      Koko.trackFlag(flagOptions);
    }

    catch (Exception e) {
      assert e.getMessage().equals("Required property flagger_id was not present.");
    }
  }

  public void testTrackModeration() {
    Map<String, String> target = new HashMap<String, String>();
    target.put("content_id", "123");

    List<Map> targets = new ArrayList();
    targets.add(target);

    Map<String, Object> moderationOptions = new HashMap<String, Object>();
    moderationOptions.put("id", "123");
    moderationOptions.put("created_at", Instant.now().toString());
    moderationOptions.put("moderator_id", "123");
    moderationOptions.put("action", "user_warned");
    moderationOptions.put("targets", targets);

    Koko.trackModeration(moderationOptions);

    moderationOptions.remove("moderator_id");

    try {
      Koko.trackModeration(moderationOptions);
    }

    catch (Exception e) {
      assert e.getMessage().equals("Required property moderator_id was not present.");
    }
  }
}
