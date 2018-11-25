import React, { Component } from 'react'
import {Button} from './Button.jsx';
import PropTypes from "prop-types";
import css from './CalculatorComponet.css';

export class CalculatorComponent extends Component {
    constructor() {
        super();
        this.state = {
            input: '',
            output: ''
        }
        this.handleClick = this.handleClick.bind(this);
        this.getServices = this.getServices.bind(this);
    }

    static propTypes = {
        handleClick: PropTypes.func,
    }
    render()
    {
        return (
            <div>
                <div>
                <input type="text" readOnly value = {this.state.input}/>
                </div>
                <div>
                <input type="text" readOnly value = {this.state.output}/>
                </div>
                <div className="button-row">
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'Clear'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'Delete'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={'('} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={')'} handleClick = {this.handleClick}/>
                </div>
                <div className="button-row">
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'7'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'8'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'9'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={'*'} handleClick = {this.handleClick}/>
                </div>
                <div className="button-row">
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'4'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'5'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'6'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={'-'} handleClick = {this.handleClick}/>
                </div>
                <div className="button-row">
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'1'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'2'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'3'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={'+'} handleClick = {this.handleClick}/>
                </div>
                <div className="button-row">
                    <Button className ={`${css.single} ${css.aquamarine}`} label={'0'} handleClick = {this.handleClick}/>
                    <Button className ={`${css.double} ${css.aquamarine}`} label={'='} handleClick = {this.handleClick}/>
                    <Button className ={`${css.single} ${css.orange}`} label={'/'} handleClick = {this.handleClick}/>

                </div>
            </div>
        );
    }

    handleClick(event){
        const value = event.target.value;
        switch (value) {
            case '=': {
                this.getServices(this.state.input)
                break;
            }
            case 'Clear': {
                this.setState({ input: '', output: '' });
                break;
            }

            case 'Delete': {
                var str = this.state.input;
                str = str.substr(0,str.length-1);
                this.setState({input: str});
                break;
            }

            default: {
                this.setState({ input: this.state.input += value})
                break;
            }
        }
    }

     getServices(input) {
         var encodedQuery = btoa(input);

         var finalQuery  = '/calculator?query='+encodedQuery

         return this.callApi(
            this.get,
            finalQuery
        )
    }

     get() {
        return {
            method: 'GET',
            headers: {
                Accept: 'application/json',
                'Content-Type': 'application/json',
            },
            credentials: 'include',
        }
    }

     callApi(method, fullUrl, body) {
        function checkStatus(response) {
                return Promise.resolve(response)
        }

        return fetch(fullUrl, method(body))
            .then(checkStatus)
            .then(response => response.json())
            .then((response) => {
                if(response.error == "false")
                this.setState({output:response.result, input:''})
                else
                    this.setState({output:'INVALID INPUT'})
            })
    }

}


