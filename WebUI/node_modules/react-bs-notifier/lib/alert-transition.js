"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require("react");

var _react2 = _interopRequireDefault(_react);

var _transitionStyles = require("./transition-styles");

var _transitionStyles2 = _interopRequireDefault(_transitionStyles);

var _reactTransitionGroup = require("react-transition-group");

var _container = require("./container");

var _reactJss = require("react-jss");

var _reactJss2 = _interopRequireDefault(_reactJss);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

var AlertTransition = function AlertTransition(_ref) {
	var classes = _ref.classes,
	    props = _objectWithoutProperties(_ref, ["classes"]);

	delete props.classes; // if it is there (it may not be depending on which version of JSS is used)
	var timeout = { enter: _container.ENTER_TIMEOUT, exit: _container.EXIT_TIMEOUT };
	return _react2.default.createElement(_reactTransitionGroup.CSSTransition, _extends({ timeout: timeout, classNames: classes }, props));
};

exports.default = (0, _reactJss2.default)(_transitionStyles2.default)(AlertTransition);