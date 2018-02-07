import React from "react";

var Icon = function Icon(_ref) {
	var type = _ref.type,
	    _ref$className = _ref.className,
	    className = _ref$className === undefined ? "" : _ref$className;

	var faType = iconType(type);

	if (faType) {
		return React.createElement("i", { className: faType + " " + className, "aria-hidden": "true" });
	}

	return null;
};

function iconType(type) {
	switch (type) {
		case "warning":
			return "fa fa-warning";

		case "info":
			return "fa fa-info";

		case "success":
			return "fa fa-check";

		case "danger":
			return "fa fa-exclamation-circle";
	}
}

export default Icon;