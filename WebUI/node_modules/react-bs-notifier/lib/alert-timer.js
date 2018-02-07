"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});
exports.PropTypes = undefined;

var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

var _react = require("react");

var _react2 = _interopRequireDefault(_react);

var _propTypes = require("prop-types");

var _alert = require("./alert");

var _alert2 = _interopRequireDefault(_alert);

var _container = require("./container");

function _interopRequireDefault(obj) { return obj && obj.__esModule ? obj : { default: obj }; }

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

var AlertTimer = function (_Component) {
	_inherits(AlertTimer, _Component);

	function AlertTimer(props) {
		_classCallCheck(this, AlertTimer);

		return _possibleConstructorReturn(this, (AlertTimer.__proto__ || Object.getPrototypeOf(AlertTimer)).call(this, props));
	}

	_createClass(AlertTimer, [{
		key: "componentDidMount",
		value: function componentDidMount() {
			this.setupTimer(this.props.timeout, this.props.onDismiss);
		}
	}, {
		key: "componentWillReceiveProps",
		value: function componentWillReceiveProps(_ref) {
			var timeout = _ref.timeout,
			    onDismiss = _ref.onDismiss;

			this.setupTimer(timeout, onDismiss);
		}
	}, {
		key: "componentWillUnmount",
		value: function componentWillUnmount() {
			// need to clean up after ourselves
			this.setupTimer();
		}
	}, {
		key: "setupTimer",
		value: function setupTimer(timeout, onDismiss) {
			if (!timeout || !onDismiss) {
				// clear any timer we currently have
				clearTimeout(this.timer);
				this.timer = null;
				this.timerTimeout = null;
			} else {
				if (this.timer && this.timerTimeout != timeout) {
					// the timeout value has changed, setup a new timer
					clearTimeout(this.timer);
					this.timer = null;
				}

				// add new timer if we don't already have one
				if (!this.timer) {
					this.timer = setTimeout(this.dismissAlert.bind(this, onDismiss), timeout + _container.ENTER_TIMEOUT + _container.EXIT_TIMEOUT);
					this.timerTimeout = timeout;
				}
			}
		}
	}, {
		key: "dismissAlert",
		value: function dismissAlert(onDismiss) {
			// clear the timer if it hasn't fired yet
			clearTimeout(this.timer);

			// we don't need to keep track of any timers for this alert anymore
			this.timer = null;
			this.timerTimeout = null;

			// actually dismiss the alert
			onDismiss();
		}
	}, {
		key: "render",
		value: function render() {
			var onDismiss = this.props.onDismiss ? this.dismissAlert.bind(this, this.props.onDismiss) : null;
			return _react2.default.createElement(_alert2.default, _extends({}, this.props, { onDismiss: onDismiss }));
		}
	}]);

	return AlertTimer;
}(_react.Component);

exports.default = AlertTimer;
var PropTypes = exports.PropTypes = {
	type: (0, _propTypes.oneOf)(["info", "success", "warning", "danger"]),
	headline: _propTypes.string,
	onDismiss: _propTypes.func,
	dismissTitle: _propTypes.string,
	showIcon: _propTypes.bool,
	timeout: _propTypes.number
};

AlertTimer.propTypes = PropTypes;