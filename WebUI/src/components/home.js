import React, { Component } from 'react'
import { Link, Route } from 'react-router-dom'
import Main from "./main";
import axios from 'axios'

class Home extends Component {

    handleClick = () => {

    }

    componentDidMount() {
        console.log('Reach Home')
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
            <div className='container Home'>
                <div className='row'>
                    <div className='col-lg-4 col-lg-offset-4 text-center MainTitle'>
                        <span className='C'>C</span>ora
                        <div className='TitleSlogan'>
                            Knowledge.Simplified!!!
                        </div>
                        <div className='OauthButton'>
                            <a className="btn btn-integration" href='https://slack.com/oauth/authorize?scope=identity.basic+identity.email+identity.avatar&client_id=304468210898.305541541543'>
                                <span className="icon slack"></span>
                                <span>Slack</span>
                            </a>
                            <a className="btn btn-integration">
                                <span className="icon google"></span>
                                <span>Google</span>
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Home