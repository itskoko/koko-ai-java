koko-ai-java
============

koko-ai-java is a Java client for https://docs.koko.ai

## Install

## Usage

Set your Koko auth key.

```java
Koko.auth = "YOUR_AUTH_KEY";
```

Track content.

```java
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
```

Track flags.

```java
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
```

Track moderation.

```java
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
```

## Building

    mvn compile

## Testing

Run the following command:

    mvn test

## License

```
WWWWWW||WWWWWW
 W W W||W W W
      ||
    ( OO )__________
     /  |           \
    /o o|    MIT     \
    \___/||_||__||_|| *
         || ||  || ||
        _||_|| _||_||
       (__|__|(__|__|
```

(The MIT License)

Copyright (c) 2017 Koko Inc. <us@itskoko.com>

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the 'Software'), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED 'AS IS', WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
