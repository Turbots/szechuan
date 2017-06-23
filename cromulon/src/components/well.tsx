import * as React from "react";

export interface IWellProps {
    text: string
}

export class Well extends React.Component<IWellProps, {}> {
    render() {
        return <div className="well">{this.props.text}</div>;
    }
}