import React, { Component } from 'react'
import axios from 'axios'

class Admin extends Component {

    render() {
       
        return (
            <div>
                <div style={{ padding: '50px 0px' }}>
                    <div className="container">
                        <div className="customjumbotron">
                            <div className="row">
                                <div className="col-md-3">
                                    {/* <profile-pic size="lg" 
                                    image-url="'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXgxKQ2hBgl1BFKaZlHzfhdG86GUKZ0jIajau7PgSh12MZM9hO'" 
                                    className="ng-scope ng-isolate-scope admin_pic">
                                    <span className="profile-pic ng-binding circle-lg" ng-bind="letters" ng-class="size_className" 
                                    style=
                                    {{backgroundImage:'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXgxKQ2hBgl1BFKaZlHzfhdG86GUKZ0jIajau7PgSh12MZM9hO&quot'}}>
                                    </span></profile-pic> */}
                                </div>
                                <div className="col-md-9">
                                    <p className="heading-2">Admin Name</p>
                                    <p className="heading-3 font-light">Welcome, Admin!</p>
                                </div>
                            </div>
                        </div>

                        <div className="row" style={{margin:'30px'}}>
                            <div className="col-md-12">
                                <div className="tab-container content-bg">
                                    <div className="scrollmenu">
                                        <a data-toggle="tab" href="#cat">Manage Categories</a>
                                        <a data-toggle="tab" href="#mod" className="active">Manage Users</a>
                                    </div>
                                </div>
                                <div className="tab-content">
                                    <div id="cat" className="tab-pane fade in active">
                                        <div className="row">
                                            <div className="col-md-9">
                                                <p className="heading-4">Manage Categories </p>
                                            </div>
                                            <div className="col-md-3" style={{textAlign:'right'}}>
                                                <button style={{marginTop:'-10px'}}
                                                type="button" 
                                                className="btn btn-default" 
                                                data-toggle="modal" data-target="#addCategory">ADD</button>
                                            </div>
                                        </div>


                                        <div className="list-view-card ng-scope">
                                            <div className="card-image">
                                                {/* <profile-pic size="md" image-url="'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/'" className="ng-isolate-scope">
                                                <span className="profile-pic ng-binding circle-md" ng-bind="letters" ng-class="size_class" 
                                                style={{backgroundImage: 'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/&quot'}}>
                                                </span></profile-pic> */}
                                            </div>
                                            <div className="card-details">
                                                <div className="card-title heading-5" style={{paddingTop:'12px'}}> Category 1</div>
                                            </div>
                                        </div>
                                        <div className="list-view-card ng-scope">
                                            <div className="card-image">
                                                <profile-pic size="md" image-url="'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/'" className="ng-isolate-scope"><span className="profile-pic ng-binding circle-md" ng-bind="letters" ng-class="size_class" 
                                                style={{backgroundImage: 'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/&quot'}}></span></profile-pic>
                                            </div>
                                            <div className="card-details">
                                                <div className="card-title heading-5" style={{paddingTop:'12px'}}> Category 2</div>
                                            </div>
                                        </div>
                                        <div className="list-view-card ng-scope">
                                            <div className="card-image">
                                                <profile-pic size="md" image-url="'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/'" className="ng-isolate-scope"><span className="profile-pic ng-binding circle-md" ng-bind="letters" ng-class="size_class" 
                                                style={{backgroundImage: 'https://static1.squarespace.com/static/563186f5e4b0d916cf7ec6ce/t/563ad7a8e4b09a4c529b97e5/1446696874049/&quot'}}></span></profile-pic>
                                            </div>
                                            <div className="card-details">
                                                <div className="card-title heading-5" style={{paddingTop:'12px'}}> Category 3</div>
                                            </div>
                                        </div>
                                    </div>
                                    <div id="mod" className="tab-pane fade in active">
                                        <div className="row">
                                            <div className="col-md-9">
                                                <p className="heading-4">Manage Users </p>
                                            </div>
                                            <div className="col-md-3" style={{textAlign:'right'}}>
                                                <input type="text" id="searchkey" placeholder="Search..." className="form-control">
                                                </input>
                                            </div>
                                        </div>
                                        <div className="table-container">
                                            <table className="table table-responsive table-hover" id="users_table" style={{backgroundColor:'white'}} >
                                                <tr>
                                                    <th className="first-column" sortable-column>
                                                        <div className="ellipsis">#</div>
                                                        <div className="header-icons">
                                                            <span className="icon icon-up-arrow-solid"></span>
                                                            <span className="icon icon-down-arrow-solid"></span>
                                                        </div>
                                                    </th>
                                                    <th sortable-column>
                                                        <div className="ellipsis">Name</div>
                                                        <div className="header-icons">
                                                            <span className="icon icon-up-arrow-solid"></span>
                                                            <span className="icon icon-down-arrow-solid"></span>
                                                        </div>
                                                    </th>
                                                    <th className="hidden-xxs" sortable-column>
                                                        <div className="ellipsis">Email</div>
                                                        <div className="header-icons">
                                                            <span className="icon icon-up-arrow-solid"></span>
                                                            <span className="icon icon-down-arrow-solid"></span>
                                                        </div>
                                                    </th>
                                                    <th className="hidden-xs">
                                                        <div className="ellipsis"></div>
                                                    </th>

                                                </tr>
                                                <tr>
                                                    <td>
                                                        <span className="ellipsis">
                                                            1
                                        </span>
                                                    </td>
                                                    <td>
                                                        <span className="ellipsis">
                                                            Viral Shah
                                        </span>
                                                    </td>
                                                    <td className="hidden-xxs">
                                                        <span className="ellipsis">
                                                            shahviral9990@gmail.com
                                        </span>
                                                    </td>
                                                    <td className="hidden-xs">
                                                        <select className="form-control" name="status">
                                                            <option>None</option>
                                                            <option>Category 1</option>
                                                            <option>Category 2</option>
                                                            <option>Category 3</option>
                                                        </select>
                                                    </td>

                                                </tr>
                                                <tr>
                                                    <td>
                                                        <span className="ellipsis">
                                                            2
                                        </span>
                                                    </td>
                                                    <td>
                                                        <span className="ellipsis">
                                                            Jayant Rana
                                        </span>
                                                    </td>
                                                    <td className="hidden-xxs">
                                                        <span className="ellipsis">
                                                            jayantrana69@gmail.com
                                        </span>
                                                    </td>
                                                    <td className="hidden-xs">
                                                        <select className="form-control" name="status">
                                                            <option>Category 1</option>
                                                            <option>None</option>
                                                            <option>Category 2</option>
                                                            <option>Category 3</option>
                                                        </select>
                                                    </td>

                                                </tr>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <div id="addCategory" className="" role="dialog">
                    <div className="modal-dialog">
                        <div className="modal-content">
                            <div className="modal-header">
                                <div className="modal-title heading-3" style={{padding:'10px 30px'}}>
                                    Add New Category
                                </div>
                            </div>
                            <div className="modal-body" id="modal-body">
                                <div className="form-group has-feedback">
                                    <label>Category Name</label>
                                    <input type="text" className="form-control" aria-describedby="input" placeholder="Enter the name of your category">
                                    </input>
                                </div>
                                <div className="form-group has-feedbsack">
                                    <label>Enter Category Image</label>
                                    <input type="file" className="form-control"></input>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default Admin