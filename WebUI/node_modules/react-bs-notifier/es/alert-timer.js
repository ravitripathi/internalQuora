var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

var _createClass = function () { function defineProperties(target, props) { for (var i = 0; i < props.length; i++) { var descriptor = props[i]; descriptor.enumerable = descriptor.enumerable || false; descriptor.configurable = true; if ("value" in descriptor) descriptor.writable = true; Object.defineProperty(target, descriptor.key, descriptor); } } return function (Constructor, protoProps, staticProps) { if (protoProps) defineProperties(Constructor.prototype, protoProps); if (staticProps) defineProperties(Constructor, staticProps); return Constructor; }; }();

function _classCallCheck(instance, Constructor) { if (!(instance instanceof Constructor)) { throw new TypeError("Cannot call a class as a function"); } }

function _possibleConstructorReturn(self, call) { if (!self) { throw new ReferenceError("this hasn't been initialised - super() hasn't been called"); } return call && (typeof call === "object" || typeof call === "function") ? call : self; }

function _inherits(subClass, superClass) { if (typeof superClass !== "function" && superClass !== null) { throw new TypeError("Super expression must either be null or a function, not " + typeof superClass); } subClass.prototype = Object.create(superClass && superClass.prototype, { constructor: { value: subClass, enumerable: false, writable: true, configurable: true } }); if (superClass) Object.setPrototypeOf ? Object.setPrototypeOf(subClass, superClass) : subClass.__proto__ = superClass; }

import React, { Component } from "react";
import { oneOf, string, func, bool, number } from "prop-types";

import Alert from "./alert";

import { ENTER_TIMEOUT, EXIT_TIMEOUT } from "./container";

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
					this.timer = setTimeout(this.dismissAlert.bind(this, onDismiss), timeout + ENTER_TIMEOUT + EXIT_TIMEOUT);
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
			return React.createElement(Alert, _extends({}, this.props, { onDismiss: onDismiss }));
		}
	}]);

	return AlertTimer;
}(Component);

export default AlertTimer;


export var PropTypes = {
	type: oneOf(["info", "success", "warning", "danger"]),
	headline: string,
	onDismiss: func,
	dismissTitle: string,
	showIcon: bool,
	timeout: number
};

AlertTimer.propTypes = PropTypes;