package dette.boutique.Views;

import java.util.List;

import dette.boutique.core.ViewImpl;
import dette.boutique.data.entities.Role;
import dette.boutique.services.RoleService;

public class RoleView extends ViewImpl<Role> {
    protected RoleService roleService;

    public RoleView(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public void create() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'create'");
    }

    public List<Role> listerRole() {
        return roleService.list();
    }

    public Role choisirRole() {
        List<Role> listRoles = listerRole();
        if (listRoles == null || listRoles.isEmpty()) {
            System.out.println("Aucun role disponible.");
            return null;
        } else {
            afficherList(listRoles);
            System.out.println("Veuillez saisir l'index du role que vous voulez choisir.");
            int choix = obtenirChoixUtilisateur(1, listRoles.size());
            return listRoles.get(choix - 1);
        }
    }
}
