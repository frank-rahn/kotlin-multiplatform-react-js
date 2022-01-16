import React from "react";
import {calculateWinner} from "./Helper";
import Board from "./Board";

class Game extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      xIsNext: true,
      stepNumber: 0,
      history: [{
        squares: Array(9).fill(null),
      }],
      showPlayerSelection: true,
    };
  }

  handleClick(i) {
    const newHistory = this.state.history.slice(0, this.state.stepNumber + 1);
    const current = newHistory[newHistory.length - 1];
    const squares = current.squares.slice();

    if (calculateWinner(squares) || squares[i]) {
      return;
    }

    squares[i] = this.state.xIsNext ? 'X' : 'O';
    this.setState({
      xIsNext: !this.state.xIsNext,
      stepNumber: newHistory.length,
      history: newHistory.concat([{
        squares: squares,
      }]),
      showPlayerSelection: false,
    });
  }

  jumpTo(step) {
    this.setState({
      stepNumber: step,
      xIsNext: (step % 2) === 0,
    });
  }

  render() {
    const history = this.state.history;
    const current = history[this.state.stepNumber];
    const winner = calculateWinner(current.squares);

    let isStepLeft = true;
    const moves = history.map((step, move) => {
      isStepLeft = step.squares.some(square => square === null);
      const desc = move ? 'Go to move #' + move : 'Go to game start';
      return (
          <li key={move}>
            <button onClick={() => this.jumpTo(move)}>{desc}</button>
          </li>
      );
    });

    let status;
    if (winner) {
      status = 'Winner: ' + winner;
    } else if (isStepLeft) {
      status = 'Next player: ' + (this.state.xIsNext ? 'X' : 'O');
    } else {
      status = 'Nobody won :(';
    }

    return (
        <div className="game">
          {this.state.showPlayerSelection && <div className="players">
            <button onClick={() => {
              this.setState({
                xIsNext: true,
                showPlayerSelection: false,
              });
            }}>Player X
            </button>
            <button onClick={() => {
              this.setState({
                xIsNext: false,
                showPlayerSelection: false,
              });
            }}>Player O
            </button>
          </div>}
          <div className="game-board">
            <Board
                squares={current.squares}
                onClick={(i) => this.handleClick(i)}
            />
          </div>
          <div className="game-info">
            <div>{status}</div>
            {(winner || !isStepLeft) && <button onClick={() => {
              this.jumpTo(0);
              this.setState({
                history: [{
                  squares: Array(9).fill(null),
                }],
              });
            }}>Start new game</button>}
            <ol>{moves}</ol>
          </div>
        </div>
    );
  }
}

export default Game;
