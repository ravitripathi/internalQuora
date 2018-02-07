import React, { Component } from 'react'
import axios from 'axios'
import { BrowserRouter as Router, Switch, Link, Route, browserHistory } from 'react-router-dom'

import Home from './home'
import Main from './main'
import IndiFeed from './indifeed'
import Profile from './profile'
import PostQuestion from './postquestion'
import Notification from './notification'
import '../style/main.css'
import Admin from './admin';
import Comment from './comment';

// {/*<div className="g-signin2" data-onsuccess="onSignIn"></div>*/}

class App extends Component {

    state = {
        isLoggedIn: false,
        user: {

        }
    }

    componentDidMount() {
        console.log('Reach')
        // axios({
        //     method: 'get',
        //     url: 'http://localhost:3000/slack'
        // })
        //     .then(function (response) {
        //         console.log(response)
        //     }.bind(this))
        //     .catch(function (error) {
        //         console.log(error)
        //     })
    }

    render() {

        return (
            <Router history={browserHistory}>
                <div>
                    <Route exact path='/' component={Home} />
                    <Route exact path='/home' component={Main}/>
                    <Route path='/home/profile/:userId' component={Profile} />
                    <Route path='/home/post' component={PostQuestion} />
                    <Route path='/home/admin' component={Admin}/>
                    <Route path='/home/comment/:answer/:answerId/:userId/:userName/:active' component={Comment}/>
                    <Route exact path='/home/feed/:component' component={IndiFeed}/>
                    <Route exact path='/home/notification' component={Notification}/>
                </div>
            </Router>


        )
    }

}

export default App