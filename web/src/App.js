import logo from './logo.svg';
import './App.css';

function App({openSock, closeSock}) {
  return (
    <div className="App">
      <header className="App-header">
        <button onClick={openSock}>open websocket</button>
        <button onClick={closeSock}>close websocket</button>
      </header>
    </div>
  );
}

export default App;
