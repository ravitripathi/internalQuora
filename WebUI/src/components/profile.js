import React, { Component } from 'react'
import { Link } from 'react-router-dom'
import Indifeed from './indifeed';
import Header from './header';
import axios from 'axios';

var USER = JSON.parse(localStorage.getItem('user'))

const USER_IP = '192.168.43.59'
const QNA_IP = '192.168.43.60'

class Profile extends Component {

    state = {
        qactive: false,
        aactive: false,
        catactive: false,
        isFeedVisible: false,
        followClicked: false,
        name: '',
        imageURL: '0',
        followers: '0',
        following: '0',
        questions: '0',
        category: '0',
        answers: '0',
        categoryList: [],
        isCategoryListShown: false,
        currentFeedDisplay: 'Questions asked by user',
        feedData: [{
            questionId: '',
            title: '',
            userId: '',
            content: '',
            timestamp: '',
        }]

    }

    componentWillMount() {

        let tempuser = this.props.match.params.userId
        console.log(tempuser)
        this.getProfile(tempuser)
        this.checkFollower(tempuser)
        this.getCategories(tempuser)
    }

    checkFollower(user) {
        axios({
            method: 'post',
            url: 'http://' + USER_IP + ':8080/user/isFollowing?followerId=' + USER.userId + '&followeeId=' + this.props.match.params.userId
        })
            .then(function (response) {
                console.log(response)
                if (response.data == true) {
                    this.setState({ followClicked: true })
                } else {
                    this.setState({ followClicked: false })
                }
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    getCategories(user) {
        axios.post('http://' + USER_IP + ':8080/user/findCategoryListName' + '?userId=' + user)
            .then(function (response) {
                console.log(response.data)
                this.setState({
                    categoryList: response.data
                })
            }.bind(this))
            .catch(function (error) {
                console.log(error);
            });
    }
    getProfile(user) {
        axios.post("http://" + USER_IP + ":8080/user/getProfile" + "?userId=" + user)
            .then(function (response) {
                console.log(response.data)
                this.setState({
                    name: response.data.name,
                    imageURL: response.data.imageUrl,
                    followers: response.data.followers,
                    following: response.data.following,
                    questions: response.data.questions,
                    category: response.data.category,
                    answers: response.data.questionAnsweredCount,
                })
            }.bind(this))
            .catch(function (error) {
                console.log(error);
            });
    }


    getAnswersGivenByUser(user) {
        const data = new FormData();
        data.append('userId', user);
        axios.post('http://' + QNA_IP + ':8080/questionAnswer/questionAnswerByUserId', data)
            .then(function (response) {
                console.log(response);
                let data = response.data
                this.setState({ currentFeedDisplay: "Questions answered by User" })
                this.setState({ feedData: data })
            }.bind(this))
            .catch(function (error) {
                console.log(error);
            });
    }

    setCategories() {
        this.setState({ currentFeedDisplay: "Categories User has contributed to" })
        this.setState({ isCategoryListShown: true })
        console.log(categoryList)
    }
    getQuestionsAskedByUser(user) {

        const data = new FormData();
        data.append('userId', user);
        axios.post("http://" + QNA_IP + ":8080/questionAnswer/getQuestionsByUserId", data)
            .then(function (response) {
                console.log(response);
                let data = response.data
                this.setState({ currentFeedDisplay: "Questions asked by user" })
                this.setState({ feedData: data })
            }.bind(this))
            .catch(function (error) {
                console.log(error);
            });
    };

    followUser = (e) => {
        axios({
            method: 'post',
            url: 'http://' + USER_IP + ':8080/user/follow?followerId=' + USER.userId + '&followeeId=' + this.props.match.params.userId
        })
            .then(function (response) {
                console.log(response)
                this.setState({ followClicked: true })
                this.getProfile(this.props.match.params.userId)
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    toggleClass(variable) {
        console.log(variable)
        if (variable == 'cat') {
            this.setState(
                {
                    qactive: false,
                    aactive: false,
                    catactive: true,
                    isFeedVisible: true,
                    isCategoryListShown:true,
                })
            this.setCategories()
        } else if (variable == 'questions') {
            this.setState({
                qactive: true,
                aactive: false,
                catactive: false,
                isFeedVisible: true,
                isCategoryListShown:false
            })
            this.getQuestionsAskedByUser(this.props.match.params.userId);


        } else {
            this.setState({
                qactive: false,
                aactive: true,
                catactive: false,
                isFeedVisible: true,
                isCategoryListShown:false
            })

            this.getAnswersGivenByUser(this.props.match.params.userId);

        }



        // this.getProfile("jayantrana69@gmail.com");
        // Indifeed.setState({active:variable});
    };



    render() {

        const { name } = this.state
        const { imageURL } = this.state
        const { followers } = this.state
        const { following } = this.state
        const { questions } = this.state
        const { category } = this.state
        const { answers } = this.state
        const { feedData } = this.state
        const { currentFeedDisplay } = this.state
        const { categoryList } = this.state
        const {isCategoryListShown} = this.state
        return (
            <div className='container Profile'>
                <Header user={USER} />
                <div className="panel-body" style={{ textAlign: 'center' }}>
                    <img className='ng-binding'
                        src={imageURL}
                        width="150" height='150' style={{ borderRadius: '50%' }}
                    />
                    <h1 id="name">{name}</h1>
                    <div className="tags" value="'Followers'">
                        <span ng-bind="value" className="tags-title ng-binding" style={{ textAlign: 'center' }}>Followers: {followers}</span>
                    </div>

                    <div className="tags" value="'Following'">
                        <span ng-bind="value" className="tags-title ng-binding" style={{ textAlign: 'center' }}>Following: {following}</span>
                    </div>

                    {
                        this.props.match.params.userId != USER.userId ?
                            <div className="tags" value="'Following'">
                                {this.state.followClicked ?
                                    <input type="submit" name="" disabled onClick={(e) => this.followUser()} value="Follow" class="btn btn-sm" />
                                    :
                                    <input type="submit" name="" onClick={(e) => this.followUser()} value="Follow" class="btn btn-sm" />
                                }

                            </div> :
                            ''
                    }

                </div>

                <div className="bucket-container ng-scope" style={{ textAlign: 'center', margin: '-10px' }}>
                    <ul>
                        <li className="bucket-list">
                            <div id="questions"
                                className={this.state.qactive ? 'buckets orange active' : 'buckets purple'}
                                onClick={(e) => this.toggleClass("questions")} >
                                <div className="buckets-content">
                                    <div className="bucket-heading">Questions</div>
                                    <div className="bucket-value">{questions}</div>
                                </div>
                            </div>
                        </li>

                        <li className="bucket-list">
                            <div id="cat"
                                className={this.state.catactive ? 'buckets orange active' : 'buckets purple'}
                                onClick={(e) => this.toggleClass("cat")}>
                                <div className="buckets-content">
                                    <div className="bucket-heading">Categories subscribed to</div>
                                    <div className="bucket-value">{categoryList.length}</div>
                                </div>
                            </div>
                        </li>

                        <li className="bucket-list">
                            <div id="ans"
                                className={this.state.aactive ? 'buckets orange active' : 'buckets purple'}
                                onClick={(e) => this.toggleClass("ans")}>
                                <div className="buckets-content">
                                    <div className="bucket-heading">Answers</div>
                                    <div className="bucket-value">{answers}</div>
                                </div>
                            </div>
                        </li>

                    </ul>


                </div>
                {!this.state.isFeedVisible ?
                    '' :
                    <div className='container Feed' style={{ marginTop: '400px' }}>
                        <div className="panel panel-warning">
                            <div className="panel-heading">
                                <h3 className="panel-title">{currentFeedDisplay}</h3>
                            </div>
                            <div className="panel-body">
                                <div className='row'>
                                    {
                                        isCategoryListShown ?

                                            categoryList.map((row, index) => (
                                                <div className='col-lg-12'>
                                                <p>{row}</p>
                                                </div>
                                            ))

                                    :
                                       feedData.map((row, index) => (
                                            <div className='col-lg-12'>
                                        <Link to={`/home/feed/${row.questionId}`}>
                                            <a><h5>{row.title}</h5></a></Link>
                                        <p>{row.content}</p>
                                        <p>{row.timestamp}</p>
                                        <hr />
                                    </div>

                                    ))
                                    }
                                </div>
                            </div>
                        </div>
                    </div>
                }

            </div>
        )
    }
}

export default Profile