"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _react = require("react");

var _react2 = _interopRequireDefault(_react);

var _reactTransitionGroup = require("react-transition-group");

var _propTypes = require("prop-types");

var _container = require("./container");

var _container2 = _interopRequireDefault(_container);

var _alertTimer = require("./alert-timer");

var _alertTimer2 = _interopRequireDefault(_alertTimer);

var _alertTransition = require("./alert-transition");

var _alertTransition2 = _interopRequireDefault(_alertTransition);

var _styles = require("./container/styles");

var _styles2 = _interopRequireDefault(_styles);

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

var AlertList = function AlertList(_ref) {
	var position = _ref.position,
	    alerts = _ref.alerts,
	    onDismiss = _ref.onDismiss,
	    classes = _ref.classes,
	    props = _objectWithoutProperties(_ref, ["position", "alerts", "onDismiss", "classes"]);

	return _react2.default.createElement(
		_container2.default,
		{ position: position, className: classes.container },
		_react2.default.createElement(
			_reactTransitionGroup.TransitionGroup,
			null,
			alerts.map(function (item) {
				var dismiss = onDismiss ? function () {
					return onDismiss(item);
				} : null;

				var message = item.message,
				    alertProps = _objectWithoutProperties(item, ["message"]);

				return _react2.default.createElement(
					_alertTransition2.default,
					{ key: item.id },
					_react2.default.createElement(
						_alertTimer2.default,
						_extends({}, props, alertProps, { onDismiss: dismiss }),
						message
					)
				);
			})
		)
	);
};

var timeout = _alertTimer.PropTypes.timeout,
    type = _alertTimer.PropTypes.type,
    headline = _alertTimer.PropTypes.headline;


AlertList.propTypes = _extends({}, _container.PropTypes, {
	alerts: (0, _propTypes.arrayOf)((0, _propTypes.shape)({
		id: _propTypes.any.isRequired,
		type: type,
		headline: headline,
		message: (0, _propTypes.oneOfType)([_propTypes.string, _propTypes.node, _propTypes.object]).isRequired
	})).isRequired,
	onDismiss: _propTypes.func,
	timeout: timeout
});

exports.default = (0, _styles2.default)(AlertList);