package com.epam.hostel.command;

import com.epam.hostel.command.impl.ApproveRequestCommand;
import com.epam.hostel.command.impl.BanUserCommand;
import com.epam.hostel.command.impl.CancelRequestCommand;
import com.epam.hostel.command.impl.ChangeLanguageCommand;
import com.epam.hostel.command.impl.ContinueRequestCommand;
import com.epam.hostel.command.impl.DenyRequestCommand;
import com.epam.hostel.command.impl.FindUserCommand;
import com.epam.hostel.command.impl.GetNotApprovedRequestsCommand;
import com.epam.hostel.command.impl.GetTodayRequestsCommand;
import com.epam.hostel.command.impl.LoginCommand;
import com.epam.hostel.command.impl.LogoutCommand;
import com.epam.hostel.command.impl.RegisterCommand;
import com.epam.hostel.command.impl.SetPaidRequestCommand;
import com.epam.hostel.command.impl.ShowUserRequestsCommand;
import com.epam.hostel.command.impl.ShowUserRequestsForAdmin;
import com.epam.hostel.command.impl.UpdateUserCommand;

public enum CommandEnum {
	REGISTER{
		{
			this.command = new RegisterCommand();
		}
	},	
	LOGOUT{
		{
			this.command = new LogoutCommand();
		}
	},
	CONTINUE_REQUEST{
		{
			this.command = new ContinueRequestCommand();
		}
	},
	FIND_USER{
		{
			this.command = new FindUserCommand();
		}
	},
	BAN_USER{
		{
			this.command = new BanUserCommand();
		}
	},	
	UPDATE_USER{
		{
			this.command = new UpdateUserCommand();
		}
	},	
	GET_NOT_APPROVED_REQUESTS{
		{
			this.command = new GetNotApprovedRequestsCommand();
		}
	},
	GET_TODAY_REQUESTS{
		{
			this.command = new GetTodayRequestsCommand();
		}
	},
	APPROVE_REQUEST{
		{
			this.command = new ApproveRequestCommand();
		}
	},
	DENY_REQUEST{
		{
			this.command = new DenyRequestCommand();
		}
	},
	SET_PAID_REQUEST{
		{
			this.command = new SetPaidRequestCommand();
		}
	},
	SHOW_USER_REQUESTS{
		{
			this.command = new ShowUserRequestsCommand();
		}
	},
	SHOW_USER_REQUESTS_ADMIN{
		{
			this.command = new ShowUserRequestsForAdmin();
		}
	},
	CANCEL_REQUEST{
		{
			this.command = new CancelRequestCommand();
		}
	},
	CHANGE_LANGUAGE{
		{
			this.command = new ChangeLanguageCommand();
		}
	},
	LOGIN{
		{
			this.command = new LoginCommand();
		}
	};
	
	Command command;	

	/**
     * @return current Command
     */
	public Command getCurrentCommand() {
        return command;
    }
}
