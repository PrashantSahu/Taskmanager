package com.prashant.taskmanager.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.prashant.taskmanager.domain.Task;
import com.prashant.taskmanager.utility.DBUtility;

public class TaskManagerService {
	
	private Connection connection;
	
	public TaskManagerService(){
		connection = DBUtility.getConnection();
	}
	
	public void addTask(Task task){
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("insert into task_list(task_name,task_description,task_priority,task_status,task_archived,task_start_time,task_end_time) values (?, ?, ?,?,?,?,?)");
			System.out.println("Task:"+task.getTask_name());
			preparedStatement.setString(1, task.getTask_name());
			preparedStatement.setString(2, task.getTask_description());
			preparedStatement.setString(3, task.getTask_priority());
			preparedStatement.setString(4, task.getTask_status());
			preparedStatement.setInt(5,0);
			
			Date dt = new Date();
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			String currentTime = sdf.format(dt);
			preparedStatement.setString(6,currentTime);
			preparedStatement.setString(7,currentTime);
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void archiveTask(int taskId) {
	try {
	PreparedStatement preparedStatement = connection
	.prepareStatement("update task_list set task_archived=true where task_id=?");
	// Parameters start with 1
	preparedStatement.setInt(1, taskId);
	preparedStatement.executeUpdate();
	
	} catch(Exception e){
		e.printStackTrace();
	}
}
	
	public void updateTask(Task task) throws ParseException {
	try {
	PreparedStatement preparedStatement = connection
	.prepareStatement("update task_list set task_name=?, task_description=?, task_priority=?,task_status=?" +
	"where task_id=?");
	preparedStatement.setString(1, task.getTask_name());
	preparedStatement.setString(2, task.getTask_description());
	
		preparedStatement.setString(3, task.getTask_priority());
		preparedStatement.setString(4, task.getTask_status());
		preparedStatement.setInt(4, task.getTask_id());
		preparedStatement.executeUpdate();
		
		}catch (Exception e) {
			e.printStackTrace();
		}
			// TODO: handle exception
		}
	
	public void changeTaskStatus(int taskId,String status) throws ParseException {
	try {
	PreparedStatement preparedStatement = connection
	.prepareStatement("update task_list set task_status=? where task_id=?");
	preparedStatement.setString(1,status);
	preparedStatement.setInt(2, taskId);
	preparedStatement.executeUpdate();
	
	
	}catch (Exception e) {
		e.printStackTrace();
	}
		// TODO: handle exception
	}

	public List<Task> getAllTasks() {
		
		Statement statement;
		List<Task> tasks = new ArrayList<Task>();
		try {
			statement = connection.createStatement();
			ResultSet rs = statement.executeQuery("select * from task_list where task_archived = 0");
			
			while(rs.next()){
				Task task = new Task();
				task.setTask_id(rs.getInt("task_id"));
				task.setTask_name(rs.getString("task_name"));
				task.setTask_description(rs.getString("task_description"));
				task.setTask_priority(rs.getString("task_priority"));
				task.setTask_status(rs.getString("task_status"));
				tasks.add(task);
			}
		
		} catch (SQLException e) {
			
			e.printStackTrace();
		}	
		
		return tasks;
	}
	
	
}