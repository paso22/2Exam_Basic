package ge.paso.Exam.Service;

import ge.paso.Exam.dto.ServerCreationDto;
import ge.paso.Exam.entities.Server;
import ge.paso.Exam.exceptions.ErrorEnum;
import ge.paso.Exam.exceptions.GeneralException;
import ge.paso.Exam.repositories.AppRepository;
import ge.paso.Exam.repositories.ServerRepository;
import ge.paso.Exam.users.User;
import ge.paso.Exam.users.UserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServerServiceImpl implements ServerService{

    private final ServerRepository serverRepository;
    private final AppRepository appRepository;

    @Override
    public void createServer(ServerCreationDto serverCreationDto, String userName) {
             Server s = ServerDtoToEntity(serverCreationDto);
             serverRepository.save(s);
    }


    @Override
    public List<Server> getFreeServers() {
        List<Server> freeServers = serverRepository.findByStatus("F").orElse(null);
        if (freeServers != null) {
            return freeServers;
        }
        throw new GeneralException(ErrorEnum.NO_FREE_SERVERS_FOUND);
    }

    @Override
    public Server chooseServer(String userName, String serverName) {
        Server s = serverRepository.findByName(serverName).orElse(null);
        User u = appRepository.findByUserName(userName).orElse(null);
        int userId = 0;
        if(u != null) {
            userId = u.getId();
        }
        if(s != null) {
            s.setStatus("U");
            s.setUserId(userId);
            appRepository.save(u);
        }
        return s;
    }

    @Override
    public void releaseServer(String userName, String serverName) {
        Server s = serverRepository.findByName(serverName).orElse(null);
        if(s != null) {
            s.setUserId(null);
            s.setStatus("F");
            serverRepository.save(s);
        }
    }

    @Override
    public void deleteUser(String superAdminName, String toDeleteUser) {
        User userToDelete = appRepository.findByUserName(toDeleteUser).orElse(null);
        List<Server> servers = serverRepository.findByUserId(userToDelete.getId()).orElse(null);
        for(Server s : servers) {
         s.setUserId(null);
         s.setStatus("F");
         serverRepository.save(s);
        }
        appRepository.delete(userToDelete);

    }

    @Override
    public void changeRole(String superAdminName, String secondUser, String role) {
        User u = appRepository.findByUserName(secondUser).orElse(null);
        if(u != null) {
            u.setRole(role.toUpperCase());
            appRepository.save(u);
        }
    }

    private Server ServerDtoToEntity(ServerCreationDto serverCreationDto) {
        return new Server(serverCreationDto.getName(),serverCreationDto.getCapacity(), serverCreationDto.getDeleteDate(), "F", null);
    }
}
