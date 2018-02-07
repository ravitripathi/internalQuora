var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

import React from "react";
import { TransitionGroup } from "react-transition-group";
import { any, arrayOf, func, node, object, oneOfType, shape, string } from "prop-types";
import Container, { PropTypes as ContainerPropTypes } from "./container";
import Alert, { PropTypes as AlertPropTypes } from "./alert-timer";
import AlertTransition from "./alert-transition";
import styles from "./container/styles";

var AlertList = function AlertList(_ref) {
	var position = _ref.position,
	    alerts = _ref.alerts,
	    onDismiss = _ref.onDismiss,
	    classes = _ref.classes,
	    props = _objectWithoutProperties(_ref, ["position", "alerts", "onDismiss", "classes"]);

	return React.createElement(
		Container,
		{ position: position, className: classes.container },
		React.createElement(
			TransitionGroup,
			null,
			alerts.map(function (item) {
				var dismiss = onDismiss ? function () {
					return onDismiss(item);
				} : null;

				var message = item.message,
				    alertProps = _objectWithoutProperties(item, ["message"]);

				return React.createElement(
					AlertTransition,
					{ key: item.id },
					React.createElement(
						Alert,
						_extends({}, props, alertProps, { onDismiss: dismiss }),
						message
					)
				);
			})
		)
	);
};

var timeout = AlertPropTypes.timeout,
    type = AlertPropTypes.type,
    headline = AlertPropTypes.headline;


AlertList.propTypes = _extends({}, ContainerPropTypes, {
	alerts: arrayOf(shape({
		id: any.isRequired,
		type: type,
		headline: headline,
		message: oneOfType([string, node, object]).isRequired
	})).isRequired,
	onDismiss: func,
	timeout: timeout
});

export default styles(AlertList);