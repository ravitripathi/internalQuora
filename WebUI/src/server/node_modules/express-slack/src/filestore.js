const fs = require('fs'),
      path = require('path'),
      rootDir = path.dirname(require.main.filename);


class FileStore {
  /**
   * Contructor
   *
   * @param {string} name - The file name
   * @param {string} dataPath - The relative path to store the file
   */
  constructor(file) {
    this.store = {};
    this.file = path.join(rootDir, file);
    this.init();
  }
  
  /**
   * Initialize the path and store
   *
   */
  init() {
    // create the directory if it doesn't exist
    let dataPath = path.dirname(this.file)
    try { let stats = fs.lstatSync(dataPath); }
    catch(err) { fs.mkdirSync(dataPath); }

    // load the store
    this.read();
  }

  /**
   * Get a single record by Id
   *
   * @param {string} id - The record key
   * @return {Promise} - A promise containing the record
   */
  get(id) {
    return Promise.resolve(this.store[id]);
  }
 
  /**
   * Save a record
   *
   * @param {string} id - The record key
   * @param {string} data - The record
   * @return {Promise} - A promise containing the record
   */
  save(id, data) {
    this.store[id] = data;
    this.write();
    return this.get(id);
  }

  /**
   * Return all records
   *
   * @return {Promise} - A promise containing all records
   */
  all() {
    return Promise.resolve(this.store);
  }

  /**
   * Load the store
   *
   * @return {Promise} - A promise containing all records
   */
  read() {
    if (fs.existsSync(this.file)) {
      let data = fs.readFileSync(this.file, 'utf8');
      if (data !== '') this.store = JSON.parse(data);
    }

    return this.all();
  }

  /**
   * Save the store
   *
   * @return {Promise} - A promise containing all records
   */
  write() {
    let result = fs.writeFileSync(this.file, JSON.stringify(this.store));
    return Promise.resolve(result);
  }
}


module.exports = FileStore;