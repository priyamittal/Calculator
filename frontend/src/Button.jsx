import React, {Component} from 'react';

export class Button extends Component {
    render()
    {
        return (
            <input
                className = {this.props.className}
                type="button"
                value={this.props.label}
                onClick = {this.props.handleClick}
            />
        )
    }
}