import { DeleteOutlined } from "@mui/icons-material";
import { Checkbox, IconButton, InputBase, ListItem, ListItemSecondaryAction, ListItemText } from "@mui/material";
import React, {useState} from "react";

const Todo = (props) => {
    const [item, setItem] = useState(props.item);
    const editItem=props.editItem;
    const [readOnly, setReadOnly]=useState(true);
    const deleteItem=props.deleteItem;
    const deleteEventHandler=()=>{
        deleteItem(item);
    }
    const editEventHandler=(e)=>{
        setItem({...item, title: e.target.value});
    }
    const checkboxEventHandler=(e)=>{
        item.done=e.target.checked
        editItem(item);
    }
    const turnOffReadOnly=()=>{
        setReadOnly(false);
    }
    const turnOnReadOnly=(e)=>{
        if(e.key==='Enter'){
            setReadOnly(true);
            editItem(item);
        }
    }
    return(
        <ListItem>
            <Checkbox checked={item.done} onChange={checkboxEventHandler} />
            <ListItemText>
                <InputBase
                    inputProps={{"aria-label":"naked",readOnly:readOnly}}
                    onClick={turnOffReadOnly}
                    onKeyDown={turnOnReadOnly}
                    onChange={editEventHandler}
                    type="text"
                    id={item.id}
                    name={item.id}
                    value={item.title}
                    multiline={true}
                    fullWidth={true}
                />
            </ListItemText>
            <ListItemSecondaryAction>
                <IconButton aria-label="Delete Todo" onClick={deleteEventHandler}>
                    <DeleteOutlined/>
                </IconButton>
            </ListItemSecondaryAction>
        </ListItem>
    );
};

export default Todo;