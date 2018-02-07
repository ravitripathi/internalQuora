"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});
exports.PropTypes = exports.EXIT_TIMEOUT = exports.ENTER_TIMEOUT = undefined;

var _react = require("react");

var _react2 = _interopRequireDefault(_react);

var _propTypes = require("prop-types");

var _reactTransitionGroup = require("react-transition-group");

var _alertTransition = require("../alert-transition");

var _alertTransition2 = _interopRequireDefault(_alertTransition);

var _styles = require("./styles");

var _styles2 = _interopRequireDefault(_styles);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

var ENTER_TIMEOUT = exports.ENTER_TIMEOUT = 500;
var EXIT_TIMEOUT = exports.EXIT_TIMEOUT = 300;

var AlertContainer = function AlertContainer(_ref) {
	var _ref$position = _ref.position,
	    position = _ref$position === undefined ? "top-right" : _ref$position,
	    children = _ref.children,
	    classes = _ref.classes;

	return _react2.default.createElement(
		"div",
		{ className: classes.container + " " + classes[position] },
		_react2.default.createElement(
			_reactTransitionGroup.TransitionGroup,
			null,
			_react.Children.map(children, function (child) {
				return child ? _react2.default.createElement(
					_alertTransition2.default,
					{ key: child.props.id },
					(0, _react.cloneElement)(child)
				) : null;
			})
		)
	);
};

var PropTypes = exports.PropTypes = {
	position: (0, _propTypes.oneOf)(["top-right", "top-left", "bottom-right", "bottom-left"])
};

AlertContainer.propTypes = PropTypes;

exports.default = (0, _styles2.default)(AlertContainer);