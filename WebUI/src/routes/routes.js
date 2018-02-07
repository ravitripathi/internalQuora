import React from 'react';
import { Route, IndexRoute } from 'react-router-dom';
import App from '../components/app';
import Home from '../components/home';
import Main from '../components/main';


export default (
    <Route path='/' component={App}>
        <IndexRoute component={Home} />
        <Route path='/home' component={Main} />
        <Route path='*' component={Home} />
    </Route>
);