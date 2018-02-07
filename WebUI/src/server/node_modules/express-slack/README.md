# Slack Express Middleware


## Usage
```js
const {PORT, SCOPE, TOKEN, CLIENT_ID, CLIENT_SECRET} = process.env,
      slack = require('express-slack'),
      express = require('express'),      
      app = express();

// the path for OAuth, slash commands, and event callbacks
app.use('/slack', slack({
  scope: SCOPE,
  token: TOKEN,
  store: 'data.json'
  client_id: CLIENT_ID,
  client_secret: CLIENT_SECRET
}));

// handle the "/test" slash commands
slack.on('/test', (payload, bot) => {
  bot.reply('works!');
});

app.listen(PORT, () => {
  console.log(`Server started on ${PORT}`);
});
```

## API

### Middleware
```js
const slack = require('express-slack'),
      express = require('express'),      
      app = express();

app.use('/slack', slack({
  scope: 'bot,commands',
  token: 'gIkuvaNzQIHg97ATvDxqgjtO',
  store: 'data/team.json'
  client_id: 'XXXXXXXXXXXX',
  client_secret: 'XXXXXXXXXXXX'
}));
```
Argument | Description
:---|:---
**scope** | The [Slack OAuth scope](https://api.slack.com/docs/oauth-scopes) to request
**client_id** | The [Slack OAuth Client Id](https://api.slack.com/docs/oauth) code
**client_secret** | The [Slack OAuth Client Secret](https://api.slack.com/docs/oauth) code
**token** | The [Slack Verification Token](https://api.slack.com/slash-commands#validating_the_command) (optional)
**store** | A string path to a filestore or a custom store object


### Events
```js
// handle RTM messages
slack.on('message', (payload, bot) => { });

// handle all slash commands
slack.on('slash_command', (payload, bot) => { });

// handle the outgoing webhooks trigger word "googlebot"
slack.on('googlebot', (payload, bot) => { });

// handle multiple events
slack.on('googlebot', '/test', 'slash_command', (payload, bot) => { });

// wildcard support
slack.on('*', (payload, bot) => { });
```
Event | Description
:---|:---
***** | All events
**message** | All RTM events
**slash_command** | All Slash Commands
**event** | All Event API callbacks
**webhook** | All WebHook callbacks
**interactive_message** | All Interactive message callbacks
**[/command]** | Any specific slash command
**[event type]** | Any [specific event](https://api.slack.com/events) type
**[trigger word]** | Any trigger from outgoing webhooks

### Bot
Bots are preloaded with the appropriate token and are context aware. So you can reply to messages and send ephemeral updates to a message.
```js
slack.on('message', (payload, bot) => {
  bot.replyPrivate('loading...');

  bot.reply({
    text: 'Everything is working!',
    attachments: [{
      title: "Slack API Documentation",
      title_link: "https://api.slack.com/",
      text: "Optional text that appears within the attachment",
      fields: [{
        title: "Priority",
        value: "High",
        short: false
      }]
    }]
  });

  // the token is already set
  bot.send('channels.info', { channel: 'C1234567890' }).then(data => {
    // results from API call
  });
});
```
Methods | Description
:---|:---
[say](src/bot.js#L50) | Send a message
[reply](src/bot.js#L22) | Send a public reply to the event
[replyPrivate](src/bot.js#L41) | Send an ephemeral reply to the event
[send](src/bot.js#L61) | Call any Slack API endpoint

### Data Store
A key/value store to maintain team/bot information and store custom setings. The store follows the same interface of a single [BotKit Store](https://github.com/howdyai/botkit#storing-information)
```js
slack.store.all().then(results => {
  // list of all items
});

slack.store.get(id).then(record => {
  // return a single record by key
});
```
Methods | Description
:---|:---
[get](src/filestore.js#L39) | Get a single record by id
[all](src/filestore.js#L61) | Get all saved records
[save](src/filestore.js#L50) | Save a record


### Client
The Slack client is a way to call the API outside of an event.
```js
let message = {
  unfurl_links: true,
  channel: 'C1QD223DS1',
  token: 'xoxb-12345678900-ABCD1234567890',
  text: "I am a test message http://slack.com",
  attachments: [{
    text: "And here's an attachment!"
  }]
}

// send message to any Slack endpoint
slack.send('chat.postMessage', message).then(data => {
  // Success!
});

// respond to webhooks
slack.send('https://hooks.slack.com/services/T0000/B000/XXXX', message);
```

#### Instances
```js
// create an instance with defaults
let instance = slack.client({
  unfurl_links: true,
  channel: 'C1QD223DS1',
  token: 'xoxb-12345678900-ABCD1234567890'  
});

let message = {
  text: "I am a test message http://slack.com",
  attachments: [{
    text: "And here's an attachment!"
  }]
};

// send message to any Slack endpoint
instance.send('chat.postMessage', message);
```