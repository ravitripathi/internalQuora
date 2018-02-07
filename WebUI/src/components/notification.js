import React, { Component } from 'react'
import axios from 'axios'
import { Link } from 'react-router-dom'
import orderBy from 'lodash/orderBy'

import Header from './header'

const BASE_URL = 'http://'
const FEED_IP = '192.168.43.122'
// const FEED_IP = '10.177.7.86'
const FEED_PORT = 8080
const NOT_END = '/notifications/findAllByUser?user_id='

var USER = JSON.parse(localStorage.getItem('user'))

class Notification extends Component {

    state = {
        notifications: [{}]
    }

    componentDidMount() {
        axios({
            method: 'post',
            url: BASE_URL + FEED_IP + ':' + FEED_PORT + NOT_END + USER.userId,
        })
            .then(function (response) {
                // console.log(response)
                this.setState({notifications: response.data})
                console.log(this.state.notifications)
                let tempnot = orderBy(this.state.notifications, 'timestamp', 'desc')
                this.setState({notifications: tempnot})
                console.log(this.state.notifications)
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    render() {
        const { notifications } = this.state

        return (
            <div className='container Notification'>
                <Header user={USER} />
                <div className="panel panel-warning">
                    <div className="panel-heading">
                        <h3 className="panel-title">Notifications</h3>
                    </div>
                    <div className="panel-body">
                        <div className='row'>
                            {notifications && notifications.map((row, index) => (
                                <div>
                                    {row.type == 3 ?
                                        <div>
                                            <p>Your question <Link to={`/home/feed/${row.details.question_id}`}>{row.details.question}</Link> got a new answer</p>
                                        </div> :
                                        ''
                                    }
                                    {row.type == 4 ?
                                        <div>
                                            <p><Link to={`/home/profile/${row.details.follower_id}`}>{row.details.name}</Link> just followed you</p>
                                        </div> :
                                        ''
                                    }
                                    {row.type == 2 ?
                                        <div>
                                            <p>New question posted on {row.details.category}</p>
                                        </div> :
                                        ''
                                    }
                                    {row.type == 1 ?
                                        <div>
                                            <p>{row.details.name} just posted a question</p>
                                        </div> :
                                        ''
                                    }
                                    <hr />
                                </div>
                            ))}
                            {/*<div className='col-lg-12 RealDesc'>*/}
                                {/*<h5>{question.content}</h5>*/}
                            {/*</div>*/}
                            {/*<div className='col-lg-12'>*/}
                                {/*{this.state.quesImage != '' ?*/}
                                    {/*<img className='img-responsive FeedImage' src={`http://10.177.7.117:8081${this.state.quesImage}`}/> :*/}
                                    {/*''*/}
                                {/*}*/}

                            {/*</div>*/}
                            {/*<div className='col-lg-3 pull-right'>By: {question.userName}</div>*/}
                            {/*<div className='col-lg-3'>Category: {question.category}</div>*/}
                            {/*<p>Tags :*/}
                                {/*{question.tags && question.tags.map((item) => (*/}
                                    {/*<span className='custom-label' key={item}>{item}</span>*/}
                                {/*))}*/}
                            {/*</p>*/}


                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Notification