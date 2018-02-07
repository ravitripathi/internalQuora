# toetag
> Use bootstrap variables in your Javascript

## Why does this exist?!?!?!

So we can `require` shared vars from bootstrap for consistent look & feel without fooling with paths to less files after an `npm dedupe`.
**If you just want to use an existing bootstrap style, just use the class and inject precompiled bootstrap.**

See [this explanation](http://caitpotter.blogspot.com/2013/08/strapless-unadulterated-twbs-without.html) by somebody who came up with a similar idea and the ~~same package name~~ package name we used to have.

## How do I use this stupid thing!!?!?!?!?!

```
npm install toetag --save
```

Then in your app's code:

ES6 Style (preferred):

```javascript
import { bootstrap } from "toetag";
```

browserify ES5 style:

```javascript
var bootstrap = require("toetag").bootstrap;
```

## Example use in some dumb React component

```javascript
import React from "react";
import { bootstrap } from "toetag";
import Color from "color-forge";

export default class MyPrettyPony extends React.Component {
	render() {
	// the pony will run away if you get its haircut too dirty
		const maneColor = Color(bootstrap.grayLighter).mix(Color(bootstrap.grayDark), (this.props.petCount / this.props.ponyPatienceThreshold);

		const ponyStyle = {
			padding: `${bootstrap.paddingBaseVertical} ${bootstrap.paddingBaseHorizontal}`,
			backgroundColor: maneColor
		};

		return (
			<div style={ponyStyle}>Be gentle</div>
		);
	}
}
```
