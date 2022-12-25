<?php
namespace App\Repositories;

use App\Models\Account;

class AccountRepository
{

    protected Account $model;
    public function __construct()
    {
        $this->model = new Account();
    }


    public function addNewDataAccount(array $requestedData): Account
    {
        return $this->model->create($requestedData);
    }

    public function suspendAccountById(string $id): int
    {
        return $this->model->where("employee_id", $id)->update(["is_active" => 0]);
    }
}
?>
