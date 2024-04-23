package dal.Interface;

import be.Personal;

import java.util.List;

public interface IPersonal {

    public List<Personal> getAllPersonal() throws Exception;
    public Personal createPersonal(Personal personal) throws Exception;

    public Personal deletePersonal(Personal personal) throws Exception;

    public Personal updatePersonal(Personal personal) throws Exception;

}
