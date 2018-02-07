import React, { Component } from 'react'
import axios from 'axios'
import { Link } from 'react-router-dom'
import { AlertList, Alert, AlertContainer } from "react-bs-notifier";

import Header from './header'

const BASE_URL = 'http://'
const INFEED_IP = '10.177.7.117'
const INFEED_PORT = 8080
const QUES_END = '/questionAnswer/getQuestionByQuestionId/?questionId='
const ANSW_END = '/questionAnswer/getAnswersByQuestionId/'

const QNA_IP = '172.16.20.48'
// const QNA_IP = '192.168.43.60'

var USER = JSON.parse(localStorage.getItem('user'))

class IndiFeed extends Component {
    state = {
        question: {
            active: true,
            category: '',
            content: '',
            imageUrl: '',
            questionId: '',
            tags: [],
            timestamp: '',
            title: '',
            userId: '',
            userName: '',
            moderatorId: ''
        },
        comment: [
            {
                commentId: '',
                answerId: '',
                comment: '',
                timestamp: '',
                userId: '',
                userName: ''
            }
        ],
        quesImage: '',
        isActive: true,
        answers: [
            {}
        ],
        isShowingSuccessAlert: false,
        isShowingDangerAlert: false,
        timeout: 1000,
        commentClicked: false,
        answerActive: 0
    }
    
    postComment() {
        let tempanswer = this.props.match.params
        console.log(tempanswer)
        let content = this.refs.commentTextArea.value;
        axios({
            method: 'post',
            url: 'http://' + QNA_IP + ':8080/questionAnswer/addComment',
            // url: 'http://10.177.7.117:8080/questionAnswer/addComment',
            data: {
                "comment": content,
                "userId": USER.userId,
                "answerId": this.props.match.params.answerId
            }
        }).then(function (response) {
            console.log(response.data)
            axios({
                method: 'get',
                url: 'http://' + QNA_IP + ':8080/questionAnswer/getCommentsByAnswerId/' + tempanswer.answerId
                // url: 'http://10.177.7.117:8080/questionAnswer/getCommentsByAnswerId/' + tempanswer.answerId
            })
                .then(function (response) {
                    console.log(response)
                    this.refs.commentTextArea.value = ''
                    let data = response.data.commentDTOList
                    let tempcomment = this.state.comment.slice()
                    tempcomment.splice()
                    this.setState({ comment: data })
                    console.log(this.state.comment)
                    this.onAlertToggle("isShowingSuccessAlert")
                }.bind(this))
                .catch(function (error) {
                    console.log(error)
                    this.onAlertToggle("isShowingDangerAlert")
                })
        }.bind(this))
            .catch(function (error) {
                console.log(error);
                this.onAlertToggle("isShowingDangerAlert")
            });
    }
    
    showComments = (event) => {
        this.setState({commentClicked: !this.state.commentClicked})
        let answerid = event.target.id
        this.setState({answerActive: answerid})
        console.log(this.state.answerActive)
        console.log(answerid)
        axios({
            method: 'get',
            url: 'http://' + QNA_IP + ':8080/questionAnswer/getCommentsByAnswerId/' + event.target.id
        })
            .then(function (response) {
                console.log('Comments')
                console.log(response)
                console.log(this.state.answerActive)
                let data = response.data.commentDTOList
                // if (this.state.comment != null) {
                //     let tempcomment = this.state.comment.slice()
                //     tempcomment.splice()
                // }
                //
                this.setState({ comment: data })
                console.log(this.state.comment)
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    postA() {
        let content = this.refs.answer.value;
        const data = new FormData();
        data.append('questionId', this.props.match.params.component);
        data.append('userId', USER.userId);
        data.append('answer', content);

        let qid = this.props.match.params.component

        if (this.state.file) {
            data.append('file', this.state.file);
            axios.post('http://' + QNA_IP + ':8080/questionAnswer/addAnswer', data)
            // axios.post('http://10.177.7.117:8080/questionAnswer/addAnswer', data)
                .then(function (response) {
                    console.log(response.data)
                    axios({
                        method: 'post',
                        url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + QUES_END + qid
                    })
                        .then(function (response) {
                            console.log(response)
                            this.refs.answer.value = ''
                            let data = response.data
                            this.setState({
                                question: data
                            })
                            axios({
                                method: 'get',
                                url: BASE_URL + QNA_IP + ':' + INFEED_PORT + ANSW_END + qid
                                // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + ANSW_END + qid
                            })
                                .then(function (response) {
                                    console.log(response)
                                    let data = response.data.answerDTOList
                                    this.setState({ answers: data })
                                    console.log('Answers')
                                    console.log(this.state.answers)
                                }.bind(this))
                                .catch(function (error) {
                                    console.log(error)
                                })
                        }.bind(this))
                        .catch(function (error) {
                            console.log(error)
                        })
              

                    this.onAlertToggle("isShowingSuccessAlert")

                }.bind(this))
                .catch(function (error) {
                    console.log(error);
                   
                    this.onAlertToggle("isShowingDangerAlert")
                });
        }
        else {
            axios.post('http://' + QNA_IP + ':8080/questionAnswer/addAnswerWithoutImage', data)
            // axios.post('http://10.177.7.117:8080/questionAnswer/addAnswerWithoutImage', data)
                .then(function (response) {
                    console.log(response.data)
                    this.refs.answer.value = ''
                    axios({
                        method: 'post',
                        url: BASE_URL + QNA_IP + ':' + INFEED_PORT + QUES_END + qid
                        // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + QUES_END + qid
                    })
                        .then(function (response) {
                            console.log(response)
                            let data = response.data
                            this.setState({
                                question: data
                            })
                            axios({
                                method: 'get',
                                url: BASE_URL + QNA_IP + ':' + INFEED_PORT + ANSW_END + qid
                                // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + ANSW_END + qid
                            })
                                .then(function (response) {
                                    console.log(response)
                                    let data = response.data.answerDTOList
                                    this.setState({ answers: data })
                                    console.log('Answers')
                                    console.log(this.state.answers)
                                }.bind(this))
                                .catch(function (error) {
                                    console.log(error)
                                })
                        }.bind(this))
                        .catch(function (error) {
                            console.log(error)
                        })
                        this.onAlertToggle("isShowingSuccessAlert")
                }.bind(this))
                .catch(function (error) {
                    console.log(error);
                    this.onAlertToggle("isShowingDangerAlert")
                });
        }
    }

    handleFile(e) {
        this.setState({ file: e.target.files[0] })
        console.log(e.target.files[0]);

    }

    activate = (event) => {
        console.log(this.refs.active.checked)
        axios({
            method: 'get',
            url: 'http://' + QNA_IP + ':8080/questionAnswer/deactivateQuestion/' + this.state.question.questionId
            // url: 'http://10.177.7.117:8080/questionAnswer/deactivateQuestion/' + this.state.question.questionId
        })
            .then(function (response) {
                console.log(response)
                let qid = this.props.match.params.component
                axios({
                    method: 'post',
                    url: BASE_URL + QNA_IP + ':' + INFEED_PORT + QUES_END + qid
                    // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + QUES_END + qid
                })
                    .then(function (response) {
                        console.log(response)
                        let data = response.data
                        this.setState({
                            question: data
                        })
                        console.log('questions')
                        console.log(this.state.question)
                        if (this.state.question.imageUrl != '') {
                            let image = this.state.question.imageUrl.split('photos')
                            this.setState({
                                quesImage: image[1]
                            })
                        }

                        console.log(this.refs.active)

                        if (this.refs.active) {
                            if (this.state.question.active == true) {
                                this.refs.active.checked = true
                            } else {
                                this.refs.active.checked = false
                            }
                        }


                        console.log(this.state.quesImage)
                        axios({
                            method: 'get',
                            url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + ANSW_END + qid
                        })
                            .then(function (response) {
                                console.log(response)
                                let data = response.data.answerDTOList
                                this.setState({ answers: data })
                                console.log('Answers')
                                console.log(this.state.answers)
                            }.bind(this))
                            .catch(function (error) {
                                console.log(error)
                            })
                    }.bind(this))
                    .catch(function (error) {
                        console.log(error)
                    })
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }


    componentDidMount() {
        console.log('Indifeed')
        console.log(this.props)
        let qid = this.props.match.params.component
        axios({
            method: 'post',
            url: BASE_URL + QNA_IP + ':' + INFEED_PORT + QUES_END + qid
            // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + QUES_END + qid
        })
            .then(function (response) {
                console.log(response)
                let data = response.data
                this.setState({
                    question: data
                })
                console.log('questions')
                console.log(this.state.question)
                if (this.state.question.imageUrl != '') {
                    let image = this.state.question.imageUrl.split('photos')
                    this.setState({
                        quesImage: image[1]
                    })
                }

                if (this.refs.active) {
                    if (this.state.question.active == true) {
                        this.refs.active.checked = true
                    } else {
                        this.refs.active.checked = false
                    }
                }

                console.log(this.state.quesImage)
                axios({
                    method: 'get',
                    url: BASE_URL + QNA_IP + ':' + INFEED_PORT + ANSW_END + qid
                    // url: BASE_URL + INFEED_IP + ':' + INFEED_PORT + ANSW_END + qid
                })
                    .then(function (response) {
                        console.log(response)
                        let data = response.data.answerDTOList
                        this.setState({ answers: data })
                        console.log('Answers')
                        console.log(this.state.answers)
                    }.bind(this))
                    .catch(function (error) {
                        console.log(error)
                    })
            }.bind(this))
            .catch(function (error) {
                console.log(error)
            })
    }

    onAlertToggle(type) {
        this.setState({
            [type]: !this.state[type]
        });
    }

    onAlertDismissed(alert) {
        this.setState({
            isShowingDangerAlert: false,
            isShowingSuccessAlert: false
        })
      
    }

    render() {
        const { comment, question, answers, timeout } = this.state

        return (
            <div className='container IndiFeed'>
                <Header user={USER} />
                <AlertContainer position="top-right">
                    {this.state.isShowingSuccessAlert ? (
                        <Alert type="success" headline="Done!"
                            onDismiss={this.onAlertDismissed.bind(this)}
                            timeout={this.state.timeout}
                        >
                            Your Answer has been posted!
						</Alert>
                    ) : null}


                    {this.state.isShowingDangerAlert ? (
                        <Alert type="danger" headline="Whoops!"
                            onDismiss={this.onAlertDismissed.bind(this)}
                            timeout={this.state.timeout}
                        >
                            Sorry, couldn't post your Answer
						</Alert>
                    ) : null}
                </AlertContainer>
                <div className="panel panel-warning">
                    <div className="panel-heading">
                        {question.title != '' && question.moderatorId == question.userId ?
                            <div>
                                <h3 className="panel-title">{question.title}</h3>
                                <div class="toggle-container options-vertical form-group pull-right">
                                    <ul>
                                        <li class="toggle-container">
                                            <input type="checkbox" id="active" ref='active' onChange={this.activate} />
                                            {question.active ?
                                                <label for="active">Active</label> :
                                                <label for="active">InActive</label>
                                            }

                                            <div class="slider"></div>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            :
                            <h3 className="panel-title">{question.title}{question.active ? <span className='ActiveSpan pull-right'>Active</span> : <span className='ActiveSpan pull-right'>InActive</span>}</h3>
                        }
                    </div>
                    <div className="panel-body">
                        <div className='row'>
                            <div className='col-lg-12 RealDesc'>
                                <h5>{question.content}</h5>
                            </div>
                       
                            <div className='col-lg-3 pull-right'>By: {question.userName}</div>
                            <div className='col-lg-3'>Category: {question.category}</div>
                            <p>Tags :
                                {question.tags && question.tags.map((item) => (
                                    <span className='custom-label' key={item}>{item}</span>
                                ))}
                            </p>


                        </div>
                    </div>
                    {answers ?
                        <div className="panel-footer">
                            <div className='row'>
                                <h4>Answers: </h4>
                                {answers.map((row, index) => (
                                    <div className='col-lg-12'>
                                        <span className='pull-right AuthorName'>By: <a>{row.userName}</a></span>
                                        <p>{row.answer}</p>
                                        {/*<Link to={`/home/comment/${row.answer}/${row.id}/${row.userId}/${row.userName}/${question.active}`}>*/}
                                            {/*View Comment*/}
                                        {/*</Link>*/}
                                        <a id={row.id} onClick={this.showComments}>View Comments</a>
    
                                        {this.state.commentClicked && this.state.answerActive == row.id ?
                                            <div className="row" style={{ marginTop: '30px' }}>
                                                <div className="col-md-12">
                
                
                                                    {comment &&
                                                    <p className="heading-4">Comments ({comment.length})</p>
                                                    }
                
                                                    {comment && comment.map((row, index) => (
                                                        <div className="list-view-card comment_card">
                                                            <div className="card-image">
                                                                <profile-pic size="md" image-url="https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/"></profile-pic>
                                                            </div>
                                                            <div className="card-details">
                                                                <div className="card-title heading-5">{row.userName}</div>
                                                                <ul className="card-sub-details">
                                                                    <li>
                                                    <span>
                                                        {row.comment}
                                                    </span>
                                                                    </li>
                                                                    <li style={{ textAlign: 'right' }}>
                                                                        <div className="text-muted" style={{ textAlign: 'right' }}>
                                                                            {row.timestamp}
                                                                        </div>
                                                                    </li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    ))}
                
                                                    <div className="form-group has-feedback input-container-md">
                                    <textarea auto-grow
                                              className="form-control"
                                              rows="1" max-lines="10"
                                              ref="commentTextArea"
                                              placeholder="Enter your comment here" ng-model="testarea2"
                                              style={{ overflowY: 'hidden', height: '96px' }}>
                                    </textarea>
                                                    </div>
                                                    <div className="form-group" style={{ textAlign: 'right' }}>
                                                        {question.active ?
                                                            <input type="submit" name="" value="Add Comment" className="btn btn-sm btn-primary" onClick={(e) => this.postComment()}>
                                                            </input>
                                                            :
                                                            <input type="submit" name="" disabled value="Add Comment" className="btn btn-sm btn-primary" onClick={(e) => this.postComment()}>
                                                            </input>
                                                        }
                
                                                    </div>
            
                                                </div>
                                            </div> :
                                            ''
                                        }
                                        <hr />
                                    </div>
                                ))}
                            </div>
                            
                            
                        </div> :
                        ''
                    }
                    <textarea ref="answer" className="form-control AnswerQues" rows="4" placeholder="Add Answer" />


                    {question.active ?
                        <button style={{ margin: '40px' }} type="button" className="btn btn-default"
                            onClick={(e) => this.postA()}>
                            Answer
                        </button>
                        :
                        <button style={{ margin: '40px' }} disabled type="button" className="btn btn-default"
                            onClick={(e) => this.postA()}>
                            Answer
                        </button>
                    }

                </div>
            </div>
        )
    }
}

export default IndiFeed