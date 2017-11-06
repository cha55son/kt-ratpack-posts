<ul>
    <#list users as user>
        <li>
            FirstName: ${user.firstName}
            LastName: ${user.lastName}
            Email: ${user.email}
        </li>
    <#else>
        No users exist at the moment
    </#list>
</ul>