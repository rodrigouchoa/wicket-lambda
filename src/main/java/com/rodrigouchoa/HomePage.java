package com.rodrigouchoa;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.validation.validator.StringValidator;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	
	/* This is our "make believe" database, where we will store our users. 
	 * Don't do this in the real world.*/
	private List<User> database = new ArrayList<>();
	
	/* Our counter to generate the user ids */
	private AtomicInteger counter = new AtomicInteger();
	
	private Form<User> form;
	private Label titleLabel;
	

	@SuppressWarnings("serial")
	public HomePage(final PageParameters parameters) {
		super(parameters);
		
		/* creating our form setting a User object as a model. */
		form = new Form<User>("form");
		form.setModel(new CompoundPropertyModel<User>(new User()));
		add(form);
		
		/* Dynamic label.
		 * If the current model user object doesn't have an id set, we're inserting a user.
		 * If it does, we are editting one.
		 * We change the label accordingly */
		titleLabel = ComponentFactory.newLabel("titleLabel", () -> form.getModelObject().getId() == null ? "Insert User" : "Edit User");
		add(titleLabel);
		
		
		TextField<String> nameTx = ComponentFactory.newTextField("name", "Name", true, StringValidator.maximumLength(50));
		form.add(nameTx);
		
		/* Button whose label value is dynamic */
		Button saveUpdateButton = ComponentFactory.newButton("saveUpdateButton",
				() -> form.getModelObject().getId() == null ? "Save" : "Update",
				() -> saveUpdate(form.getModelObject()));
		form.add(saveUpdateButton);
		
		ListView<User> listView = new ListView<User>("listView", database) {
			@Override
			protected void populateItem(ListItem<User> item) {
				item.add(new Label("name"));
				item.add(ComponentFactory.newAjaxLink("editLink", (target) -> edit(target, item)));
			}
			
			@Override
			protected IModel<User> getListItemModel(IModel<? extends java.util.List<User>> listViewModel, int index) {
				User user = listViewModel.getObject().get(index);
				return new CompoundPropertyModel<User>(user);
			}
		};
		form.add(listView);
    }
	
	/* method invoked by the "Save/Update" button. */
	private void saveUpdate(User user) {
		//if there's no id, it's a new user.
		if (user.getId() == null) {
			user.setId(counter.incrementAndGet());
			database.add(user);
			
		} else {
			// otherwise, we're updating an existing user
			// we'll just change the name of the object already
			// in the database.
			// ps: we find the user with the matching id using some streams/lambda magic
			List<User> users = database.stream().filter((currentUser) -> currentUser.getId().equals(user.getId())).collect(Collectors.toList());
			users.get(0).setName(user.getName()); // there should be always one single user in the resulting collection
		}
		
		clearForm(); //clears the screen
	}
	
	/* method invoked by the "Edit" link */
	private void edit(AjaxRequestTarget target, ListItem<User> item) {
		form.setDefaultModelObject(item.getModelObject());
		if (target != null) {
			target.add(form);
			target.add(titleLabel);
		}
	}
	
	//clears the form
	private void clearForm() {
		this.form.setModelObject(new User());
		this.form.clearInput();
	}
	
}
