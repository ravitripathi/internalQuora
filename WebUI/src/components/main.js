import React, { Component } from 'react'
import { Route, Link } from 'react-router-dom'
import axios from 'axios'
import { debounce } from 'throttle-debounce';

import SideNav, { Nav, NavIcon, NavText } from 'react-sidenav';

import Header from './header'

const BASE_URL = 'http://'
const LOGIN_IP = '192.168.43.59'
// const LOGIN_IP = '10.177.7.61'
const LOGIN_PORT = 8080
const LOGIN_END = '/user/login'

const FEED_IP = '192.168.43.122'
// const FEED_IP = '10.177.7.86'
const FEED_PORT = 8080
const FEED_END = '/notifications/feed'

const SEARCH_IP = '192.168.43.157'
// const SEARCH_IP = '10.177.7.93'
const SEARCH_PORT = 8080
const SEARCH_END = '/question/autoSuggest/'

const QNA_IP = '192.168.43.60'
const ADMIN_IP = '192.168.43.186'

var USER = JSON.parse(localStorage.getItem('user'))

class Main extends Component {

    state = {
        user: USER,
        isSearchOn: false,
        searchQuery: '',
        searched: [{
            category: '',
            questionId: '',
            tags: [],
            title: '',
            userName: '',
            userId: ''
        }],
        category: [{}],
        feedEntered: false,
        showFeed: false,
        sport: [{
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: ''
        }],
        food: [{
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: ''
        }],
        technology: [{
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: ''
        }],
        news: [{
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: ''
        }],
        general: [{
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: ''
        }],
        feed: [
            {
                active: true,
                category: '',
                content: '',
                imageUrl: '',
                questionId: '',
                tags: '',
                timestamp: '',
                title: '',
                userId: ''
            }
        ],

        categoryDetails: [
            {
                categoryId: '',
                categoryName: '',
                imageUrl: '',
                numberOfQuestions: ''
            }
        ],

        heading: 'Feed',
        indiCategory: [{}]

    }
    addSearch = (event) => {

        // setTimeout(this.callSearch(event), 10000)
        event.persist()
        debounce(1000, () => {
            console.log('value :: ', event.target.value);
            if (event.target.value == '') {
                this.setState({ isSearchOn: false })
                this.setState({ showFeed: true })
            } else {
                this.setState({ isSearchOn: true })
                this.setState({ showFeed: false })
                axios({
                    method: 'get',
                    url: BASE_URL + SEARCH_IP + ':' + SEARCH_PORT + SEARCH_END + event.target.value
                })
                    .then(function (response) {
                        let data = response.data
                        console.log(data)
                        let tempsearch = this.state.searched.slice()
                        tempsearch.splice(0, 100)
                        for (var i = 0; i < data.length; i++) {
                            tempsearch.push({
                                category: data[i].category,
                                questionId: data[i].questionId,
                                tags: data[i].tags,
                                title: data[i].title,
                                userName: data[i].userName,
                                userId: data[i].userId
                            })
                        }

                        this.setState({ searched: tempsearch })

                        console.log('Searched')
                        console.log(this.state.searched)
                    }.bind(this))
                    .catch(function (error) {
                        console.log(error)
                    })
            }

        })()
    }

    getCatLink(catName) {
        console.log(catName)
        const datum = new FormData();
        datum.append('category', catName);
        axios({
            method: 'post',
            url: 'http://' + QNA_IP + ':8080/questionAnswer/getQuestionsByCategory',
            // url: 'http://10.177.7.117:8080/questionAnswer/getQuestionsByCategory',
            data: datum

        })
            .then(function (response) {
                console.log(response)
                let data = response.data
                // this.setState({category:data.category})

                console.log(this.state.category)
                this.setState({ heading: catName })
                if (this.state.heading == 'Sport') {
                    this.setState({ indiCategory: this.state.category.Sport })
                } else if (this.state.heading == 'News') {
                    this.setState({ indiCategory: this.state.category.News })
                } else if (this.state.heading == 'General') {
                    this.setState({ indiCategory: this.state.category.General })
                } else if (this.state.heading == 'Food') {
                    this.setState({ indiCategory: this.state.category.Food })
                } else if (this.state.heading == 'Technology') {
                    this.setState({ indiCategory: this.state.category.Technology })
                }
                let tempcat = this.state.category
                console.log('tempcat')
                console.log(tempcat)
                this.setState({ feedEntered: false })
                this.setState({ isSearchOn: false })
                // this.setState({ feedEntered: false })
                // this.setState({ showFeed: true })
                // this.setState({ category: data })
                // this.setState({ sport: this.state.category.Sport })
                // this.setState({ news: this.state.category.News })
                // this.setState({ general: this.state.category.General })
                // this.setState({ technology: this.state.category.Technology })
                // this.setState({ food: this.state.category.Food })
                // console.log('catcat')
                // console.log(data)
                // console.log(this.state.category)

            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })

    }


    componentWillMount() {

        console.log('Component Will Mount')

        if (!USER || this.state.user == null) {
            let code = this.props.location.search;
            code = code.split('=')[1]
            code = code.split('&')[0]
            console.log(code)
            console.log('Reach Main')
            axios({
                method: 'get',
                url: 'https://slack.com/api/oauth.access?client_id=304468210898.305541541543&client_secret=d93eb31dd7fbb9217ceeb713da523b2d&code=' + code,
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded'
                }
            })
                .then(function (response) {

                    console.log(response)
                    let name = response.data.user.name
                    let userId = response.data.user.email
                    let imageUrl = response.data.user.image_24
                    let id = response.data.user.id


                    // this.setState({user: {name: name, userId: userId, imageUrl: imageUrl, id: id}})
                    // console.log(this.state.user)

                    console.log(userId)
                    if (userId != null) {
                        let tempuser = {
                            name: name,
                            userId: userId,
                            id: id,
                            imageUrl: imageUrl
                        }

                        localStorage.setItem('user', JSON.stringify(tempuser));
                        let token = response.data.access_token

                        axios({
                            method: 'post',
                            url: BASE_URL + LOGIN_IP + ':' + LOGIN_PORT + LOGIN_END,
                            data: {
                                name: name,
                                imageUrl: imageUrl,
                                userId: userId,
                                role: 1
                            }
                        })
                            .then(function (respo) {
                                console.log(respo)

                            })
                            .catch(function (er) {
                                console.log(er)
                            })

                        axios({
                            method: 'post',
                            url: BASE_URL + FEED_IP + ':' + FEED_PORT + FEED_END + '?user_id=' + userId
                        })
                            .then(function (response) {
                                console.log(response)
                                let data = response.data
                                this.setState({ feedEntered: true })
                                this.setState({ showFeed: true })
                                this.setState({ category: data })
                                this.setState({ sport: this.state.category.Sport })
                                this.setState({ news: this.state.category.News })
                                this.setState({ general: this.state.category.General })
                                this.setState({ technology: this.state.category.Technology })
                                this.setState({ food: this.state.category.Food })
                                console.log(this.state.sport)

                            }.bind(this))
                            .catch(function (error) {
                                console.log(error)
                            })
                    }


                }.bind(this))
                .catch(function (err) {
                    console.log(err)
                })

        } else {

            axios({
                method: 'post',
                url: BASE_URL + FEED_IP + ':' + FEED_PORT + FEED_END + '?user_id=' + USER.userId
            })
                .then(function (response) {
                    console.log(response)
                    let data = response.data
                    this.setState({ feedEntered: true })
                    this.setState({ showFeed: true })
                    this.setState({ category: data })
                    this.setState({ sport: this.state.category.Sport })
                    this.setState({ news: this.state.category.News })
                    this.setState({ general: this.state.category.General })
                    this.setState({ technology: this.state.category.Technology })
                    this.setState({ food: this.state.category.Food })
                    console.log(this.state.sport)

                }.bind(this))
                .catch(function (error) {
                    console.log(error)
                })
        }

        axios({
            method: 'get',
            url: 'http://' + ADMIN_IP + ':8080/getAll'
        })
            .then(function (response) {
                this.setState({ categoryDetails: response.data })
                console.log(response.data)
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    componentDidMount() {

        if (this.state.searched[0].questionId == '') {
            this.state.searched.splice(0, 1);
        }
        console.log('Main' + this.state.isSearchOn)
    }

    render() {
        const { indiCategory, showFeed, isSearchOn, searched, sport, news, food, general, technology, categoryDetails, heading } = this.state

        return (
            <div className='container Main'>
                <Header user={USER} addSearch={this.addSearch.bind(this)} />
                <row>
                    <div className='col-lg-2' >
                        <div style={{ border: '2px', borderColor: '#4D4341', background: '', color: '#000000', width: 200, marginTop: '80px', marginLeft: '-50px' }}>
                            <SideNav>
                                <h3><center>Categories</center></h3>
                                {categoryDetails.map((row, index) => (
                                    <div>
                                        {row.categoryName != 'Bot' ?
                                            <Nav id='dashboard'>
                                                <NavIcon><img src={row.imageUrl} style={{ width: '30px', height: '30px', borderRadius: '50%' }} /></NavIcon>
                                                <NavText><a onClick={(e) => this.getCatLink(`${row.categoryName}`)}>{row.categoryName}&nbsp;</a>({row.numberOfQuestions})</NavText>
                                                <hr />
                                            </Nav> :
                                            ''
                                        }
                                    </div>
                                ))}
                            </SideNav>
                        </div>
                    </div>

                    <div className='col-lg-10'>
                        {isSearchOn ?
                            <div className='container Search'>
                                <div className="panel panel-warning">
                                    <div className="panel-heading">
                                        <h3 className="panel-title">Search</h3>
                                    </div>
                                    <div className="panel-body">
                                        {searched.length > 0 && (
                                            <div>
                                                {searched.map((row, index) => (
                                                    <div className='col-lg-12' eventKey={index} key={index}>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>

                                                        <h6>Category: {row.category}</h6>
                                                        <div>
                                                            <p>Tags :
                                                        {row.tags && row.tags.map((item) => (
                                                                    <span className='custom-label' key={item}>{item}</span>
                                                                ))}
                                                            </p>
                                                        </div>
                                                        <hr />
                                                    </div>
                                                ))}
                                            </div>
                                        )}
                                    </div>
                                </div>
                            </div> :
                            <div className='container Feed'>
                                <div className="panel panel-warning">
                                    <div className="panel-heading">
                                        <h3 className="panel-title">{heading}</h3>
                                    </div>
                                    <div className="panel-body">
                                        <div className='row'>
                                            {indiCategory && indiCategory.map((row, index) => (
                                                <div className='col-lg-12'>
                                                    <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                    <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                    <h6>Category: {row.category}</h6>
                                                    <p>{row.content}</p>
                                                    <p>Tags :
                                {row.tags && row.tags.map((item) => (
                                                            <span className='custom-label' key={item}>{item}</span>
                                                        ))}
                                                    </p>
                                                    <hr />
                                                </div>
                                            ))}
                                        </div>
                                    </div>
                                </div>
                            </div>
                        }

                        {showFeed ?
                            <div className='container Feed'>
                                <div className="panel panel-warning">
                                    <div className="panel-heading">
                                        <h3 className="panel-title">{heading}</h3>
                                    </div>
                                    {this.state.feedEntered ?
                                        <div className="panel-body">
                                            <div className='row'>
                                                {sport && sport.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                    {row.tags && row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                                {news && news.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                    {row.tags && row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                                {technology && technology.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                    {row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                                {general && general.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                    {row.tags && row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                                {food && food.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                    {row.tags && row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                                {/*<div className='col-lg-4'>*/}
                                                {/*<img className='img-responsive' src='../img/2013-lebron-11-away-commercial-04.jpg' />*/}
                                                {/*</div>*/}
                                            </div>

                                        </div> :
                                        <div className="panel-body">
                                            <div className='row'>
                                                {indiCategory && indiCategory.map((row, index) => (
                                                    <div className='col-lg-12'>
                                                        <Link to={`/home/feed/${row.questionId}`}><a><h5>{row.title}</h5></a></Link>
                                                        <Link to={`/home/profile/${row.userId}`}><span className='pull-right AuthorName'>By: <a>{row.userName}</a></span></Link>
                                                        <h6>Category: {row.category}</h6>
                                                        <p>{row.content}</p>
                                                        <p>Tags :
                                                {row.tags && row.tags.map((item) => (
                                                                <span className='custom-label' key={item}>{item}</span>
                                                            ))}
                                                        </p>
                                                        <hr />
                                                    </div>
                                                ))}
                                            </div>
                                        </div>
                                    }
                                </div>
                            </div> :
                            ''
                        }

                    </div>


                </row>





            </div>
        )
    }
}

export default Main