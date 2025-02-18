import Todo from './Todo';
import AddTodo from './AddTodo'
import './App.css';
import React,{useEffect, useState} from 'react';
import { AppBar, Button, Container, Grid, List, Paper, Toolbar, Typography } from '@mui/material';
import { call, signout } from './service/ApiService';

function App() {
  const [items,setItems]=useState([]);
  const [loading,setLoading]=useState(true);
  useEffect(()=>{
    call("/todo", "GET", null)
    .then((response)=>{
      setItems(response.data)
      setLoading(false);
  });
  },[]);

  const deleteItem=(item)=>{
    call("/todo","DELETE",item)
    .then((response)=>setItems(response.data));
  }
  const addItem = (item) => {
    call("/todo","POST",item)
    .then((response)=>setItems(response.data));
  }
  const editItem=(item)=>{
    call("/todo","PUT",item)
    .then((response)=>setItems(response.data));
  }
  let todoItems = items.length > 0 && (
    <Paper style={{margin: 16}}>
      <List>
        {items.map((item)=> (
          <Todo item={item} key={item.id} editItem={editItem} deleteItem={deleteItem}/>
        ))}
      </List>
    </Paper>
  );
  let navigationBar=(
    <AppBar position='static'>
      <Toolbar>
        <Grid justifyContent="space-between" container>
          <Grid items>
            <Typography variant='h6'>오늘의 할일</Typography>
          </Grid>
          <Grid item>
            <Button color='inherit' raised onClick={signout}>
              로그아웃
            </Button>
          </Grid>
        </Grid>
      </Toolbar>
    </AppBar>
  );
  let todoListPage = (
    <div className="App">
      {navigationBar}
      <Container maxWidth="md">
        <AddTodo addItem={addItem}/>
        <div className='TodoList'>{todoItems}</div>
      </Container>
    </div>
  );
  let loadingPage=<h1>로딩중...</h1>;
  let content=loadingPage;
  if (!loading){
    content=todoListPage;
  }

  return <div className='App'>{content}</div>
}

export default App;
/*
{
    id: "0",
    title: "Hello World 1",
    done: true
  },
  {
    id: "1",
    title: "Hello World 2",
    done: false
  }


<header className="App-header">
        <img src={logo} className="App-logo" alt="logo" />
        <p>
          Edit <code>src/App.js</code> and save to reload.
        </p>
        <a
          className="App-link"
          href="https://reactjs.org"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn React
        </a>
      </header>
*/