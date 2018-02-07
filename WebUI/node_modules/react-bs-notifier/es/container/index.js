import React, { Children, cloneElement } from "react";
import { oneOf } from "prop-types";
import { TransitionGroup } from "react-transition-group";
import AlertTransition from "../alert-transition";
import styles from "./styles";

export var ENTER_TIMEOUT = 500;
export var EXIT_TIMEOUT = 300;

var AlertContainer = function AlertContainer(_ref) {
	var _ref$position = _ref.position,
	    position = _ref$position === undefined ? "top-right" : _ref$position,
	    children = _ref.children,
	    classes = _ref.classes;

	return React.createElement(
		"div",
		{ className: classes.container + " " + classes[position] },
		React.createElement(
			TransitionGroup,
			null,
			Children.map(children, function (child) {
				return child ? React.createElement(
					AlertTransition,
					{ key: child.props.id },
					cloneElement(child)
				) : null;
			})
		)
	);
};

export var PropTypes = {
	position: oneOf(["top-right", "top-left", "bottom-right", "bottom-left"])
};

AlertContainer.propTypes = PropTypes;

export default styles(AlertContainer);