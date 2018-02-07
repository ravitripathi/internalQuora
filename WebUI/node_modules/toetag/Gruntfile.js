var grunt = require("grunt");

module.exports = function(grunt) {

grunt.initConfig({
  less2js: {
    letsgo: {
      options: {
        format: 'json',
        camelCase: true
      },
			src: ['node_modules/bootstrap/less/variables.less'],
			dest: 'lib/bootstrap-variables.json'
    }
  }
});
}

grunt.loadNpmTasks('grunt-less2js');

grunt.registerTask('default', ['less2js']);
