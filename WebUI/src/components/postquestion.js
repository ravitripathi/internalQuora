import React, { Component } from 'react'
import { Link, withRouter } from 'react-router-dom'
import axios from 'axios';
import SkyLight from 'react-skylight';
import Header from './header'
import { List } from 'immutable'
import { TagBox } from 'react-tag-box'
import { AlertList, Alert, AlertContainer } from "react-bs-notifier";


var USER = JSON.parse(localStorage.getItem('user'))

const QNA_IP = '192.168.43.60'

const sampleTags = List(
    [].map(t => ({
        value: t
    }))
)
var questionId;

class PostQuestion extends Component {


    state = {
        showModal: false,
        showDrop: false,
        dropdownVal: 'Category',
        tags: sampleTags,
        selected: sampleTags.take(0),
        finalTAG: [],
        file: null,
        questionId: null,
        buttonClass: 'btn btn-primary btn-lg ng-scope ng-isolate-scope',
        isShowingSuccessAlert: false,
        isShowingDangerAlert: false,
        timeout: 1000,
    }


    toggleDrop() {
        let current = this.state.showDrop;
        console.log(current)
        this.setState({ showDrop: !current })
    }

    handleChange(tags) {
        this.setState({ tags: tags })
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
        this.props.history.push("/home")
    }

    handleDelete(i) {
        let tags = this.state.tags;
        tags.splice(i, 1);
        this.setState({ tags: tags });
    }

    handleAddition(tag) {
        let tags = this.state.finalTAG;
        tags.push({
            id: tags.length + 1,
            text: tag
        });
        this.setState({ finalTAG: tags });
    }

    postQ() {
        let content = this.refs.descTextArea.value;
        let title = this.refs.titleTextArea.value;
        this.setState({
            buttonClass: 'btn btn-primary btn-lg ng-scope ng-isolate-scope button-loader-primary button-loader-lg'
        })
        let val = this.state.selected.toJS();
        let valarr = []
        for (var i = 0; i < val.length; i++) {
            valarr.push(val[i].value)
        }

        axios({
            method: 'post',
            url: 'http://' + QNA_IP + ':8080/questionAnswer/addQuestion',
            data: {
                "title": title,
                "userId": USER.userId,
                "content": content,
                "active": true,
                "category": this.state.dropdownVal,
                "tags": valarr
            }
        }).then(function (response) {
            this.setState({ buttonClass: 'btn btn-primary btn-lg ng-scope ng-isolate-scope' })
            console.log(response.data)
            this.setState({ questionId: response.data })
            questionId = response.data;

            this.onAlertToggle("isShowingSuccessAlert")


        }.bind(this))
            .catch(function (error) {
                console.log(error);
                this.setState({ buttonClass: 'btn btn-primary btn-lg ng-scope ng-isolate-scope' })
                this.onAlertToggle("isShowingDangerAlert")
            });


    }
    render() {

        const { tags, selected, buttonClass, timeout } = this.state
        const onSelect = tag => {
            const newTag = {
                label: tag.label,
                value: tag.value || tag.label
            }

            this.setState({
                selected: selected.push(newTag)
            })
        }

        const remove = tag => {
            this.setState({
                selected: selected.filter(t => t.value !== tag.value)
            })
        }

        const placeholder = selected.isEmpty() ? '' :
            "Add tags. Use the backspace key to delete the last tag"


        return (
            <div className='container Profile'>
                <AlertContainer position="top-right">
                    {this.state.isShowingSuccessAlert ? (
                        <Alert type="success" headline="Done!"
                            onDismiss={this.onAlertDismissed.bind(this)}
                            timeout={this.state.timeout}
                        >
                            Your question has been posted!
						</Alert>
                    ) : null}


                    {this.state.isShowingDangerAlert ? (
                        <Alert type="danger" headline="Whoops!"
                            onDismiss={this.onAlertDismissed.bind(this)}
                            timeout={this.state.timeout}
                        >
                            Sorry, couldn't post you question
						</Alert>
                    ) : null}
                </AlertContainer>
                <Header user={USER} />
                <div className="panel-body" style={{ textAlign: 'center' }}>
                    <h1>Post a new Question</h1>
                    <div className="form-group has-feedback input-container-md">
                        <textarea
                            auto-grow=""
                            className="form-control ng-valid ng-dirty ng-valid-parse ng-touched ng-empty"
                            rows="2" max-lines="10" placeholder="Question Title"
                            ng-model="testarea2" name="message"
                            placeholder="Enter the question"
                            style={{ overflowY: 'scroll', height: '48px' }}
                            ref='titleTextArea'
                            value={this.state.titleTextAreaVal}
                            onChange={(event) => {
                                this.setState({
                                    titleTextAreaVal: event.target.value
                                });
                            }}
                        >
                        </textarea>
                    </div>
                    <div className="form-group has-feedback input-container-md">
                        <textarea
                            label="Description"
                            auto-grow=""
                            className="form-control ng-valid ng-dirty ng-valid-parse ng-touched ng-empty"
                            rows="4" max-lines="10" placeholder="Describe your question"
                            ng-model="testarea2" name="message"
                            style={{ overflowY: 'scroll', height: '96px' }}
                            ref='descTextArea'
                            value={this.state.descTextAreaVal}
                            onChange={(event) => {
                                this.setState({
                                    descTextAreaVal: event.target.value
                                });
                            }}
                        >
                        </textarea>

                        <h3>Add Tags: </h3>
                        <TagBox
                            tags={tags.toJS()}
                            selected={selected.toJS()}
                            onSelect={onSelect}
                            removeTag={remove}
                            backspaceDelete={true}
                            placeholder={placeholder}
                        />

                        <div className={this.state.showDrop ? "btn-group ng-scope dropdown open" : "btn-group ng-scope dropdown"} uib-dropdown=""
                            onClick={(event) => { this.toggleDrop() }}>
                            <button className="btn btn-primary btn-lg ng-scope ng-isolate-scope btn-action dropdown-toggle" type="button" uib-dropdown-toggle="" aria-haspopup="true" aria-expanded={this.state.showDrop ? "true" : "false"}>
                                {this.state.dropdownVal}
                                <span className="icon-down-arrow-solid"></span>
                            </button>
                            <ul className="dropdown-menu" uib-dropdown-menu="" role="menu">
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'General' }) }}>General</a>
                                </li>
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'Food' }) }}>Food</a>
                                </li>
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'Sport' }) }}>Sport</a>
                                </li>
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'News' }) }}>News</a>
                                </li>
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'Tech' }) }}>Tech</a>
                                </li>
                                <li role="menuitem">
                                    <a onClick={(event) => { this.setState({ dropdownVal: 'Bot' }) }}>Bot</a>
                                </li>
                            </ul>
                        </div>


                        <button style={{ margin: '40px' }}
                            type="button"
                            className={this.state.buttonClass}
                            button-loader="button_loader"
                            button-loader-type="primary" button-loader-size="lg"
                            onClick={(e) => this.postQ()}>
                            Post it!
                        </button>


                    </div>
                </div>
            </div>

        )
    }
}

export default PostQuestion