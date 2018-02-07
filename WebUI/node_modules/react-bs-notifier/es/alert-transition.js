var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

function _objectWithoutProperties(obj, keys) { var target = {}; for (var i in obj) { if (keys.indexOf(i) >= 0) continue; if (!Object.prototype.hasOwnProperty.call(obj, i)) continue; target[i] = obj[i]; } return target; }

import React from "react";
import transitionStyles from "./transition-styles";
import { CSSTransition } from "react-transition-group";
import { ENTER_TIMEOUT, EXIT_TIMEOUT } from "./container";
import useSheet from "react-jss";

var AlertTransition = function AlertTransition(_ref) {
	var classes = _ref.classes,
	    props = _objectWithoutProperties(_ref, ["classes"]);

	delete props.classes; // if it is there (it may not be depending on which version of JSS is used)
	var timeout = { enter: ENTER_TIMEOUT, exit: EXIT_TIMEOUT };
	return React.createElement(CSSTransition, _extends({ timeout: timeout, classNames: classes }, props));
};

export default useSheet(transitionStyles)(AlertTransition);