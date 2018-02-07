const express = require('express'),
      client = require('./client'),
      bodyParser = require('body-parser'),
      Controller = require('./controller');

let controller = null;


/**
 * Middleware Initializer
 *
 * @param {mixed} settings - The middleware settings
 * @return {Router} - A mapped express router
 */
function slack(settings) {
  let router = express.Router();
  controller = new Controller(settings);

  // expose the data store
  slack.store = controller.store;

  // body parser for json
  router.use(bodyParser.urlencoded({ extended: false }));
  router.use(bodyParser.json());

  // auth middleware
  router.get('/', controller.oauth.bind(controller));
  router.post('/', controller.verification.bind(controller));
  router.post('/', controller.challenge.bind(controller));
  
  // custom middleware
  if (settings.middleware && Array.isArray(settings.middleware))
    settings.middleware.forEach(m => router.post('/', m.bind(slack)));

  // process message
  router.post('/', controller.message.bind(controller));

  return router;
}


/**
 * Event handler for incoming messages
 *
 * @param {mixed} names - Any number of event names to listen to. The last will be the callback
 */
slack.on = (...names) => {
  let callback = names.pop(); // support multiple events per callback
  names.forEach(name => controller.on(name, callback));
}


/**
 * Create an instance of the Slack client
 *
 * @param {object} args - The default message data
 * @return {Client} A new instance of the Slack client
 */
slack.client = args => client.create(args);


/**
 * Send data to Slack's API
 *
 * @param {string} endPoint - The method name or url (optional - defaults to chat.postMessage)
 * @param {object} args - The JSON payload to send
 * @return {Promise} A promise with the API result
 */
slack.send = (...args) => client.send(...args);


module.exports = slack;