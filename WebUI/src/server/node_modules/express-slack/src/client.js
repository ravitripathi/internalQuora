const axios = require('axios'),
      WebSocket = require('ws'),
      qs = require('querystring');

const AUTH_PARAMS = ["client_id", "scope", "redirect_uri", "team", "state"],
      TOKEN_PARAMS = ["client_id", "client_secret", "code", "redirect_uri"];


class Client {
  /**
   * Contructor
   *
   * @param {Object} defaults - The default config for the instance
   */
  constructor(defaults) {    
    this.defaults = defaults || {}; // message defaults
    
    this.api = axios.create({
      baseURL: 'https://slack.com/api/',
      headers: { 'user-agent': 'express-slack' }
    });
  }

  /**
   * Create an instance of the Slack client
   *
   * @param {object} defaults - The default message data
   * @return {Client} A new instance of the Slack client
   */
  create(defaults) {
    let options = Object.assign({}, this.defaults, defaults);
    return new this.constructor(options);
  }

  /**
   * Send data to Slack's API
   *
   * @param {string} endPoint - The method name or url (optional - defaults to chat.postMessage)
   * @param {object} args - The JSON payload to send
   * @return {Promise} A promise with the API result
   */
  send(...args) {
    let endPoint = 'chat.postMessage'; // default action is post message

    // if an endpoint was passed in, use it
    if (typeof args[0] === 'string') endPoint = args.shift();

    // use defaults when available
    let message = Object.assign({}, this.defaults, ...args);  

    // call update if ts included
    if (message.ts && endPoint === 'chat.postMessage') endPoint = 'chat.update';

    return this.post(endPoint, message);
  }

  /**
   * Start RTM
   *
   * @param {object} options - Optional arguments to pass to the rtm.start method
   * @return {WebSocket} A promise containing the WebSocket
   */
  rtm(options) {
    let res = r => Promise.resolve(new WebSocket(r.url));
    return this.send('rtm.start', options).then(res);
  }

  /**
   * OAuth Authorization Url
   *
   * @param {object} params - The OAuth querystring params
   * @return {string} The authorization url
   */
  authorizeUrl(params) {
    let whitelisted = AUTH_PARAMS.reduce((keys, key) => {
      if (params[key]) keys[key] = params[key];
      return keys;
    }, {});
    return "https://slack.com/oauth/authorize?" + qs.stringify(whitelisted);
  }

  /**
   * OAuth Access
   *
   * @param {object} params - The authorization params
   * @return {promise} A Promise containing the authorization results
   */
  access(params) {
    let whitelisted = TOKEN_PARAMS.reduce((keys, key) => {
      if (params[key]) keys[key] = params[key];
      return keys;
    }, {});
    return this.post('oauth.access', whitelisted);
  }

  /**
   * Test Token
   *
   * @param {object} params - The authorization params
   * @return {promise} A Promise containing the authorization results
   */
  test(auth) {
    let params = { token: auth.access_token };
    let res = info => Promise.resolve(Object.assign({}, info, auth));
    return this.post('auth.test', params).then(res);
  }

  /**
   * Install App
   *
   * @param {object} params - The authorization params with code
   * @return {promise} A Promise containing the installation results
   */
  install(params) {
    return this.access(params).then(this.test.bind(this));
  }


  /**
   * POST data to Slack's API
   *
   * @param {string} endPoint - The method name or url
   * @param {object} payload - The JSON payload to send
   * @param {boolean} stringify - Flag to stringify the JSON body
   * @return {Promise} A promise with the api result
   */
  post(endPoint, payload, stringify) {
    if (!/^http/i.test(endPoint) || stringify === true) {
      
      // serialize JSON params
      if (payload.attachments)
        payload.attachments = JSON.stringify(payload.attachments);

      // serialize JSON for POST
      payload = qs.stringify(payload);
    }

    let res = r => {      
      if (r.data.ok && r.data.ok === false) return Promise.reject(r.data);
      else return Promise.resolve(r.data);
    }

    return this.api.post(endPoint, payload).then(res);
  }
}

module.exports = new Client();