<?php
namespace App\Repositories;

use App\Models\Account;
use App\Models\Employee;

class EmployeeRepository
{

    protected Employee $model;
    public function __construct()
    {
        $this->model = new Employee();
    }


    public function addNewDataEmployee(array $requestedData): Employee
    {
        return $this->model->create($requestedData);
    }
}

?>
