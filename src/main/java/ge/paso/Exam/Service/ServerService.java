package ge.paso.Exam.Service;

import ge.paso.Exam.dto.ServerCreationDto;
import ge.paso.Exam.entities.Server;

import java.util.List;

public interface ServerService {


    void createServer(ServerCreationDto serverCreationDto, String userName);

    List<Server> getFreeServers();

    Server chooseServer(String userName, String serverName);

    void releaseServer(String userName, String serverName);

    void deleteUser(String superAdminName, String toDeleteUser);

    void changeRole(String superAdminName, String secondUser, String role);
}
