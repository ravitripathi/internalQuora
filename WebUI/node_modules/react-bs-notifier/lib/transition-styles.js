"use strict";

Object.defineProperty(exports, "__esModule", {
	value: true
});
var MAGICAL_MAX_HEIGHT = exports.MAGICAL_MAX_HEIGHT = "20em";

exports.default = {
	enter: {
		opacity: 0.01,
		transform: "translateX(-25%)",
		maxHeight: 0,
		overflow: "hidden",
		transition: ".25s ease-in"
	},
	enterActive: {
		opacity: 1,
		transform: "translateX(0)",
		maxHeight: MAGICAL_MAX_HEIGHT
	},
	exit: {
		opacity: 1,
		transform: "translateX(0)",
		maxHeight: MAGICAL_MAX_HEIGHT,
		overflow: "hidden",
		transition: ".25s ease-out"
	},
	exitActive: {
		opacity: 0.01,
		transform: "translateX(25%)",
		maxHeight: 0
	}
};