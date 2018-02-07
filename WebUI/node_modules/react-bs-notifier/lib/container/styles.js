"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _toetag = require("toetag");

var _reactJss = require("react-jss");

var _reactJss2 = _interopRequireDefault(_reactJss);

var _transitionStyles = require("../transition-styles");

var _transitionStyles2 = _interopRequireDefault(_transitionStyles);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

exports.default = (0, _reactJss2.default)(_extends({
	container: {
		position: "fixed",
		paddingTop: _toetag.bootstrap.paddingBaseVertical,
		paddingRight: _toetag.bootstrap.paddingBaseHorizontal,
		paddingBottom: _toetag.bootstrap.paddingBaseVertical,
		paddingLeft: _toetag.bootstrap.paddingBaseHorizontal,
		zIndex: _toetag.bootstrap.zindexNavbarFixed + 1
	},
	"top-right": {
		top: 0,
		right: 0,
		textAlign: "right"
	},
	"top-left": {
		top: 0,
		left: 0
	},
	"bottom-right": {
		bottom: 0,
		right: 0,
		textAlign: "right"
	},
	"bottom-left": {
		bottom: 0,
		left: 0
	}
}, _transitionStyles2.default));