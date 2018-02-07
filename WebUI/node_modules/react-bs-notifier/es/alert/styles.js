var _extends = Object.assign || function (target) { for (var i = 1; i < arguments.length; i++) { var source = arguments[i]; for (var key in source) { if (Object.prototype.hasOwnProperty.call(source, key)) { target[key] = source[key]; } } } return target; };

import { bootstrap } from "toetag";
import useSheet from "react-jss";
import transitionStyles from "../transition-styles";

export default useSheet(_extends({}, transitionStyles, {
	alert: {
		composes: "alert",
		marginBottom: bootstrap.paddingBaseVertical,
		display: "inline-block",
		textAlign: "left"
	},
	info: {
		composes: "alert-info"
	},
	success: {
		composes: "alert-success"
	},
	warning: {
		composes: "alert-warning"
	},
	danger: {
		composes: "alert-danger"
	},
	dismissable: {
		composes: "alert-dismissable"
	},
	close: {
		composes: "close"
	},
	msgContainer: {
		display: "inline-block"
	},
	icon: {
		verticalAlign: "top",
		fontSize: bootstrap.fontSizeH4,
		paddingRight: bootstrap.paddingLargeHorizontal,
		opacity: 0.2
	},
	headline: {
		margin: 0,
		marginBottom: bootstrap.paddingBaseVertical
	},
	body: {
		maxWidth: "40em"
	}
}));